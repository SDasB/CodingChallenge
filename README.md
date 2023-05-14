# CodingChallenge
Challenge  The module code-challenge already contains the basic structure of a Spring Boot application for you.  The bookings to be stored have the following fields: - booking_id - description - price - currency - subscription_start_date - email - department


Please define as many departments as you will like, and create a unique method doBusiness() for each department. This is your time to shine, so the method implementations can be as simple, elegant, or complicated as you want.

Feel free to select the best data type that, in your opinion, would define those fields the best.

You should not use a database or some other sort of persistence but come up with an own data structure to store the transactions.

In any moment where the implementation implies the usage of an external server (i.e. sending e-mail's) feel free to mock creatively these external resources, keeping in mind that all real-world use-cases must be covered.

In general, we are looking for a good implementation, code quality, code resilience, code extensibility, code maintainability and how the implementation is tested. Basically something you wouldn't be too embarrassed to push to production.

API Specification:

POST /bookingservice/bookings

Sample Body:

{"description": "Cool description!", "price": 50.00, "currency": USD, "subscription_start_date": 683124845000, "email": "valid@email.ok", "department": "cool department"}

Creates a new booking and sends an e-mail with the details.

PUT /bookingservice/bookings/{booking_id}

Sample Body:

{"description": "Cool description!", "price": 50.00, "currency": USD, "subscription_start_date": 683124845000, "email": "valid@email.ok", "department": "another_department" }

Insert, replace if already exists a booking.

GET /bookingservice/bookings/{booking_id}

Returns the specified booking as JSON.

GET /bookingservice/bookings/department/{department}

Returns a JSON list of all bookings ids with the given department.

GET /bookingservice/bookings/currencies

Returns a JSON list with all used currencies in the existing bookings.

GET /bookingservice/sum/{currency}

Returns the sum of all bookings prices with the given currency.

GET /bookingservice/bookings/dobusiness/{booking_id}

Returns the result of doBusiness() for the given booking corresponding department.
