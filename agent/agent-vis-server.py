from flask import request, Flask


import pygame

app = Flask(__name__)

agents = {}

@app.route("/agent/<id>", methods=["PUT","POST","GET","DELETE"])
def agent(id):
    if request.method == 'GET':
        return id

    if request.method == 'POST':
        agents.put(id,"--")
        print(agents)


for i in range(0,199999):
    print(i)