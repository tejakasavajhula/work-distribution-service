# Update Task Status upon Completion

## API Description
* This API updated the status of the task and the assigned agent upon completion

## URI Patterns
  - PUT http://localhost:8445//v1/task/{taskId}

### Response
> Sample Response

 ```json
{
    "taskId": "988668323844973568",
    "assignedAgentId": "16",
    "status": "completed",
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
* 200 OK

