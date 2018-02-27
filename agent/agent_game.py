from movement import DisplayServer
import random

ds = DisplayServer()

names = ("A", "B", "C")

ids = []

display = DisplayServer()
display.remove_layer("layer0")

for name in names:
    ids.append(ds.add_label("layer0", name, 100, 100))


for i in range(0,1000):
    for id in ids:
        ds.update_pos("layer0", id, 200 + 10*random.randrange(-10,10), 200 + 10*random.randrange(-10,10))
