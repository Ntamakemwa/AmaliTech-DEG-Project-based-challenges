\# Pulse-Check-API (Watchdog Sentinel)



A Dead Man's Switch API for monitoring remote devices at CritMon Servers Inc. Devices register a monitor with a countdown timer. If no heartbeat is received before the timer expires, an alert is automatically fired.



\---



\## Architecture Diagram



\### State Flowchart



\- \[\*] --> ACTIVE : POST /monitors (register)

\- ACTIVE --> ACTIVE : POST /monitors/{id}/heartbeat (reset timer)

\- ACTIVE --> DOWN : Timer expires (no heartbeat)

\- ACTIVE --> PAUSED : POST /monitors/{id}/pause

\- PAUSED --> ACTIVE : POST /monitors/{id}/heartbeat (auto-resume)

\- DOWN --> \[\*] : Alert fired and logged to history



\### Sequence Diagram



1\. Device sends POST /monitors with id, timeout, alert\_email

2\. API starts countdown timer

3\. API returns 201 Created

4\. Device sends POST /monitors/{id}/heartbeat

5\. API resets countdown

6\. API returns 200 OK

7\. If countdown reaches 0, Alert is fired and logged



\---



\## Setup Instructions



\### Prerequisites

\- Java 21

\- Maven 3.9+



\### Run the project



git clone https://github.com/Ntamakemwa/AmaliTech-DEG-Project-based-challenges.git

cd AmaliTech-DEG-Project-based-challenges/pulse-check-api

mvn spring-boot:run



Server runs on http://localhost:8080





\---



\## API Documentation



\### 1. Register a Monitor

POST /monitors



Request:

{

&#x20; "id": "device-123",

&#x20; "timeout": 60,

&#x20; "alert\_email": "admin@critmon.com"

}



Response 201 Created:

{

&#x20; "id": "device-123",

&#x20; "timeout": 60,

&#x20; "alertEmail": "admin@critmon.com",

&#x20; "status": "active",

&#x20; "lastHeartbeat": "2026-05-23T05:42:08Z",

&#x20; "expiresAt": "2026-05-23T05:43:08Z"

}



\---



\### 2. Send Heartbeat

POST /monitors/{id}/heartbeat



Response 200 OK:

{

&#x20; "id": "device-123",

&#x20; "status": "active",

&#x20; "expiresAt": "2026-05-23T05:44:08Z"

}



Response 404 Not Found:

{

&#x20; "error": "Monitor not found: device-123"

}



\---



\### 3. Pause a Monitor

POST /monitors/{id}/pause



Response 200 OK:

{

&#x20; "id": "device-123",

&#x20; "status": "paused"

}



\---



\### 4. Get Monitor Status

GET /monitors/{id}



Response 200 OK:

{

&#x20; "id": "device-123",

&#x20; "status": "active"

}



\---



\### 5. Get All Monitors

GET /monitors



Response 200 OK:

\[

&#x20; {

&#x20;   "id": "device-123",

&#x20;   "status": "active"

&#x20; }

]



\---



\### 6. Get Alert History

GET /monitors/{id}/history



Response 200 OK:

\[

&#x20; {

&#x20;   "deviceId": "device-123",

&#x20;   "message": "Device device-123 is down!",

&#x20;   "timestamp": "2026-05-23T05:42:19Z"

&#x20; }

]



\---



\## Developer's Choice: Alert History Log



\### What it is

A GET /monitors/{id}/history endpoint that returns a full log of every alert fired for a device.



\### Why I added it

In a real monitoring system, knowing that a device is currently down is not enough. Support engineers need to know how many times a device has gone down and exactly when. This history log gives the team full visibility into a device's reliability over time, helping them decide whether to repair or replace a device. It also makes the system auditable, which is critical for infrastructure monitoring.



\---



\## Monitor Status Values



Status - Description

active - Timer is running, heartbeats being received

down - Timer expired, alert has been fired

paused - Timer stopped, no alerts will fire

