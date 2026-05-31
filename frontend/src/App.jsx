import { useState } from 'react'
import './App.css'

const initialForm = {
  budget: '',
  fuelType: '',
  familySize: '',
  priority: '',
}

const fuelTypes = ['PETROL', 'DIESEL', 'CNG', 'ELECTRIC', 'HYBRID']
const priorities = ['MILEAGE', 'SAFETY', 'PERFORMANCE']

function App() {
  const [formData, setFormData] = useState(initialForm)
  const [recommendations, setRecommendations] = useState([])
  const [isLoading, setIsLoading] = useState(false)
  const [error, setError] = useState('')
  const [showResultsPage, setShowResultsPage] = useState(false)

  const handleChange = (event) => {
    const { name, value } = event.target
    const nextValue = name === 'budget' || name === 'familySize'
      ? value === '' ? '' : Number(value)
      : value

    setFormData((currentFormData) => ({
      ...currentFormData,
      [name]: nextValue,
    }))
  }

  const handleSubmit = async (event) => {
    event.preventDefault()
    setIsLoading(true)
    setError('')
    setRecommendations([])
    setShowResultsPage(false)

    if (!isValidBudget(formData.budget)) {
      setError('Budget must be greater than 99,999')
      setIsLoading(false)
      return
    }

    try {
      console.log('Submitting payload:', formData)

      const response = await fetch('/api/recommend', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(formData),
      })

      const result = await response.json()

      if (!response.ok || !result.success) {
        throw new Error(result.error?.message || 'Unable to fetch recommendations')
      }

      if (result.data.length === 0) {
        throw new Error('No cars found within your budget. Please increase your budget and try again.')
      }

      setRecommendations(result.data)
      setShowResultsPage(true)
    } catch (caughtError) {
      setError(caughtError.message)
    } finally {
      setIsLoading(false)
    }
  }

  const handleSearchAgain = () => {
    setFormData(initialForm)
    setRecommendations([])
    setError('')
    setShowResultsPage(false)
  }

  if (showResultsPage) {
    return (
      <ResultsPage
        recommendations={recommendations}
        onSearchAgain={handleSearchAgain}
      />
    )
  }

  return (
    <main className="app-shell">
      <section className="hero-section">
        <p className="eyebrow">CarDekho Recommendation System</p>
        <h1>Find the right car for your needs</h1>
        <p className="hero-copy">
          Enter your budget and preferences to get the top 3 recommended cars
          from the backend scoring API.
        </p>
      </section>

      <section className="form-page">
        <form className="recommendation-form" onSubmit={handleSubmit}>
          <div className="form-header">
            <h2>Recommendation Details</h2>
            <p>All fields are required.</p>
          </div>

          <label>
            Budget
            <input
              min="1"
              name="budget"
              type="number"
              value={formData.budget}
              onChange={handleChange}
              placeholder="Enter budget greater than 99999, e.g. 1200000"
              required
            />
          </label>

          <label>
            Fuel Type
            <select name="fuelType" value={formData.fuelType} onChange={handleChange} required>
              <option value="" disabled>
                Select fuel type
              </option>
              {fuelTypes.map((fuelType) => (
                <option key={fuelType} value={fuelType}>
                  {fuelType}
                </option>
              ))}
            </select>
          </label>

          <label>
            Family Size
            <input
              min="1"
              max="10"
              name="familySize"
              type="number"
              value={formData.familySize}
              onChange={handleChange}
              placeholder="Enter number of family members, e.g. 5"
              required
            />
          </label>

          <label>
            Priority
            <select name="priority" value={formData.priority} onChange={handleChange} required>
              <option value="" disabled>
                Select your top priority
              </option>
              {priorities.map((priority) => (
                <option key={priority} value={priority}>
                  {priority}
                </option>
              ))}
            </select>
          </label>

          <button type="submit" disabled={isLoading}>
            {isLoading ? 'Finding cars...' : 'Get Recommendations'}
          </button>

          {error && <p className="error-message">{error}</p>}
          {isLoading && <div className="empty-state">Calling recommendation API...</div>}
        </form>
      </section>
    </main>
  )
}

function ResultsPage({ recommendations, onSearchAgain }) {
  const summary = buildRecommendationSummary(recommendations)

  return (
    <main className="app-shell results-page">
      <section className="results-hero">
        <button className="secondary-button" type="button" onClick={onSearchAgain}>
          Search Again
        </button>
        <p className="eyebrow">Recommendation Results</p>
        <h1>Top 3 Recommended Cars</h1>
        <p className="hero-copy">
          These cars are sorted by recommendation score from the backend API.
        </p>
      </section>

      <section className="results-card-grid">
        {recommendations.map((car, index) => (
          <article className="result-card" key={car.id}>
            <div className="result-rank">#{index + 1}</div>
            <div className="card-topline">
              <div>
                <h2>
                  {car.brand} {car.model}
                </h2>
                <p>
                  {car.fuelType} | {car.transmission}
                </p>
              </div>
              <span className="score">{car.score}</span>
            </div>

            <dl className="result-stats">
              <div>
                <dt>Recommendation Score</dt>
                <dd>{car.score}</dd>
              </div>
              <div>
                <dt>Price</dt>
                <dd>{formatCurrency(car.price)}</dd>
              </div>
              <div>
                <dt>Mileage</dt>
                <dd>{car.mileage} km/l</dd>
              </div>
              <div>
                <dt>Safety Rating</dt>
                <dd>{car.safetyRating}/5</dd>
              </div>
            </dl>
          </article>
        ))}
      </section>

      <section className="ai-summary-section">
        <div className="ai-summary-header">
          <p className="eyebrow">AI Recommendation Summary</p>
          <h2>Buyer-friendly recommendation guide</h2>
          <p>
            A simple summary based on score, budget value, mileage, safety, and seating capacity.
          </p>
        </div>

        <div className="best-pick-card">
          <span>Best Overall Pick</span>
          <h3>
            {summary.bestOverall.brand} {summary.bestOverall.model}
          </h3>
          <p>{summary.bestOverallReason}</p>
        </div>

        <div className="summary-grid">
          {summary.carSummaries.map((carSummary) => (
            <article className="summary-card" key={carSummary.id}>
              <h3>{carSummary.name}</h3>
              <p>{carSummary.reason}</p>

              <div className="summary-list-grid">
                <div>
                  <h4>Pros</h4>
                  <ul>
                    {carSummary.pros.map((pro) => (
                      <li key={pro}>{pro}</li>
                    ))}
                  </ul>
                </div>

                <div>
                  <h4>Cons</h4>
                  <ul>
                    {carSummary.cons.map((con) => (
                      <li key={con}>{con}</li>
                    ))}
                  </ul>
                </div>
              </div>
            </article>
          ))}
        </div>
      </section>

      <section className="comparison-section">
        <div className="comparison-header">
          <h2>Compare Recommended Cars</h2>
          <p>Quick comparison of the top 3 recommendations.</p>
        </div>

        <div className="comparison-table-wrapper">
          <table className="comparison-table">
            <thead>
              <tr>
                <th>Car</th>
                <th>Price</th>
                <th>Mileage</th>
                <th>Safety Rating</th>
                <th>Seating Capacity</th>
              </tr>
            </thead>
            <tbody>
              {recommendations.map((car) => (
                <tr key={car.id}>
                  <td>
                    {car.brand} {car.model}
                  </td>
                  <td>{formatCurrency(car.price)}</td>
                  <td>{car.mileage} km/l</td>
                  <td>{car.safetyRating}/5</td>
                  <td>{car.seatingCapacity}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </section>
    </main>
  )
}

const formatCurrency = (amount) =>
  new Intl.NumberFormat('en-IN', {
    style: 'currency',
    currency: 'INR',
    maximumFractionDigits: 0,
  }).format(amount)

const isValidBudget = (budget) => Number(budget) > 99999

const buildRecommendationSummary = (cars) => {
  const bestOverall = cars[0]

  return {
    bestOverall,
    bestOverallReason: `${bestOverall.brand} ${bestOverall.model} has the highest recommendation score (${bestOverall.score}), making it the strongest overall match among the top 3 cars.`,
    carSummaries: cars.map((car) => ({
      id: car.id,
      name: `${car.brand} ${car.model}`,
      reason: buildBuyerReason(car),
      pros: buildPros(car),
      cons: buildCons(car),
    })),
  }
}

const buildBuyerReason = (car) =>
  `${car.brand} ${car.model} is recommended because it offers a score of ${car.score}, costs ${formatCurrency(
    car.price,
  )}, delivers ${car.mileage} km/l mileage, and has a ${car.safetyRating}/5 safety rating.`

const buildPros = (car) => {
  const pros = [
    `Strong recommendation score of ${car.score}`,
    `Mileage of ${car.mileage} km/l`,
  ]

  if (car.safetyRating >= 4) {
    pros.push(`Good safety rating of ${car.safetyRating}/5`)
  }

  if (car.seatingCapacity >= 7) {
    pros.push('Good option for larger families')
  }

  return pros
}

const buildCons = (car) => {
  const cons = []

  if (car.safetyRating < 4) {
    cons.push(`Safety rating is moderate at ${car.safetyRating}/5`)
  }

  if (car.seatingCapacity < 5) {
    cons.push('Seating capacity may be limited for family use')
  }

  if (car.mileage < 18) {
    cons.push('Mileage is lower compared with more efficiency-focused cars')
  }

  return cons.length > 0 ? cons : ['No major drawback based on the selected preferences']
}

export default App
