# Get All Active Agents Information

## API Description
* This API retrieves information about all the active agents and the tasks each of them is assigned

## URI Patterns
  - GET http://localhost:8445/v1/agents

### Response
> Sample Response

 ```json
[
    {
        "agent": {
            "agentId": "12",
            "skills": [
                "skill1",
                "skill2",
                "skill3"
            ],
            "status": "busy",
            "latestActivityTimestamp": "2019-08-26T22:15:17.333+0000"
        },
        "taskId": "9118907054129645568",
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
]
 ```
* 200 OK

