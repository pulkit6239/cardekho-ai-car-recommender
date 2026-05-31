# AI Car Recommendation System
Full-stack car recommendation app built for the CarDekho Software Engineer AI-Native take-home assignment.
## What I Built
I built a web app that helps a confused car buyer move from "I don't know what to buy" to a clear shortlist.
The user enters their budget, fuel preference, family size, and main buying priority. The backend scores cars from an in-memory catalog and returns the top 3 recommendations with explanation-friendly data.
## What I Deliberately Cut
I deliberately cut login, real database persistence, advanced filters, car images, reviews analysis, and a complex AI chat interface.
For the 2-3 hour time box, I focused on the highest-value flow: collecting buyer intent and returning a ranked shortlist.
## Tech Stack
- Frontend: React + Vite
- Backend: Java + Spring Boot
- Data: In-memory car catalog
- API: REST API with validation and structured responses
I picked this stack because it allowed me to ship a clean full-stack app quickly with readable backend logic and a simple interactive frontend.
## How To Run Locally
### Backend
```bash
cd backend
mvn spring-boot:run
Backend runs on:

http://localhost:8081
Frontend
cd frontend
npm install
npm run dev
Frontend runs on:

http://localhost:5173
API
POST /api/recommend

Example request:

{
  "budget": 1200000,
  "fuelType": "PETROL",
  "familySize": 5,
  "priority": "SAFETY"
}
AI Tool Usage
I used Cursor as the main AI coding assistant. I delegated boilerplate setup, frontend layout, API wiring, DTO structure, and README drafting support to AI.

I manually reviewed the generated code, checked the API contract, adjusted the scoring logic, tested the end-to-end flow, and made the product scoping decisions.

The tools helped most with speed and structure. They got in the way when generated code needed cleanup, naming consistency, or verification against actual runtime behavior.
