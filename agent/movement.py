import pymongo


class DisplayServer(object):

    def __init__(self, host='localhost', port=27017, db="game"):

        self.db = pymongo.MongoClient(host=host, port=port).get_database(db)
        self._counter = 0

    def add_label(self, layer, name, X, Y):
        self._counter += 1
        self.db.get_collection(layer).insert_one({
            "_id": self._counter,
            "name": name,
            "X": X,
            "Y": Y,
            "type": "LABEL"
        })

        return self._counter

    def remove_obj(self, layer, _id):

        self.db.get_collection(layer).delete_one({"_id": _id})
        return id

    def get_obj(self, layer, _id):

        self.db.get_collection(layer).find_one({"_id": _id})

    def add_rect(self, layer, X, Y, W, H):
        self._counter += 1
        self.db.get_collection(layer).insert_one({
            "_id": self._counter,
            "X": X,
            "Y": Y,
            "W": W,
            "H": H,
            "type": "RECT"
        })

        return self._counter

    def update_pos(self, layer, _id, X, Y):

        id = {"_id": _id}
        data = self.db.get_collection(layer).find_one(id)
        data["X"] = X
        data["Y"] = Y
        self.db.get_collection(layer).update_one(id, {"$set": data})

    def remove_layer(self, layer):

        self.db.get_collection(layer).drop()

    def remove_all_layers(self):

        layers = self.db.get_collection("layers")

        for layer in layers.find():
            self.remove_layer(layer.get("_id"))


