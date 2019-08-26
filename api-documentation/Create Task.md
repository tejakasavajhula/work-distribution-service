# Create Task and Assign an Agent

## API Description
* This API creates a new task and assign an agent with all the skills necessary for the task

## URI Patterns
  - POST http://localhost:8445/v1/task

### Request

> Sample Request

 ```json
{
	"taskDescription":"3 Request",
	"requesterName":"Teja",
	"priority":"high",
	"skills":["skill1","skill2","skill3"]
}
 ```

### Response
> Sample Response

 ```json
{
    "taskId": "988668323844973568",
    "assignedAgentId": "16",
    "status": "assigned",
    "taskDescription": "3 Request",
    "requesterName": "Teja",
    "priority": "high",
    "skills": [
        "skill1",
        "skill2",
        "skill3"
    ]
}
 ```
* 201 CREATED

