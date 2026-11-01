"""Comprueba que ambas réplicas usan la misma base de datos y limpia la tarea."""

import datetime
import json
import urllib.request


def request(base, path, method="GET", body=None):
    data = None if body is None else json.dumps(body).encode()
    req = urllib.request.Request(base + path, data=data, method=method,
                                 headers={"Content-Type": "application/json"})
    with urllib.request.urlopen(req, timeout=15) as response:
        payload = response.read()
        return json.loads(payload) if payload else None


def main():
    first, second = "http://localhost:8081", "http://localhost:8082"
    for base in (first, second):
        assert request(base, "/actuator/health")["status"] == "UP"
    task = request(first, "/api/tasks", "POST", {
        "title": "Smoke test entre replicas", "status": "PENDING", "priority": 3,
        "dueDate": str(datetime.date.today() + datetime.timedelta(days=7))})
    path = "/api/tasks/" + task["id"]
    try:
        assert request(second, path) == task
        print("CORRECTO: ambas replicas UP y tarea creada en app1 recuperada desde app2.")
    finally:
        request(first, path, "DELETE")


if __name__ == "__main__":
    main()
