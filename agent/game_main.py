import pygame as pg
import sys
import json
import pymongo
from abc import ABC, abstractmethod


DISPLAY_MODE = (1200,800)
DISPLAY_CAPTION = "Agent Sim"
BG_COLOR = (230, 230, 230)
FG_COLOR = (0,0,0)

pg.init()
FONT = pg.font.SysFont("monospace", 15)

# Agent: sense, plan, act


class Drawable(ABC):

    TYPES = ("RECT", "IMAGE", "LABEL")

    def __init__(self, xy):

        self.xy = xy

    @abstractmethod
    def draw(self, screen):
        pass

    def draw_obj(self, screen, obj):

        obj_rect = obj.get_rect()
        obj_rect.topleft = self.xy
        screen.blit(obj, obj_rect)

    @classmethod
    def decide_object_type(cls, data):

        _type = data.get("type")
        color = (data.get("R"), data.get("G"), data.get("B"))
        xy = (data.get("X"), data.get("Y"))

        if _type is None:
            print("Unknown drawable type")
            return

        if _type == Drawable.TYPES[0]:
            wh = (data.get("W"), data.get("H"))
            if color is None:
                return Rectangle(xy, wh)
            else:
                return Rectangle(xy, wh, color=color)

        elif _type == Drawable.TYPES[1]:

            return Image(xy, data.get("image_path"))

        elif _type == Drawable.TYPES[2]:

            color = data.get("color")
            if color is None:
                return Label(xy, data.get("name"))
            else:
                return Label(xy, data.get("name"), color)


class Image(Drawable):

    def __init__(self, xy, image_path):

        super(Drawable, self).__init__(xy)
        self.image_path = image_path

    def draw(self, screen):
        image = pg.image.load(self.image_path)
        self.draw_obj(screen, image)


class Label(Drawable):

    def __init__(self,  xy, label, color=FG_COLOR, font=FONT):

        super().__init__(xy)
        self.label = label
        self.font = font
        self.color = color

    def draw(self, screen):
        label = self.font.render(self.label, 2, self.color)
        self.draw_obj(screen, label)


class Rectangle(Drawable):

    def __init__(self,  xy, size, color=FG_COLOR):

        super().__init__(xy)
        self.size = size
        self.color = color

    def draw(self, screen):
        pg.draw.rect(screen, self.color, (self.xy[0], self.xy[1], self.size[0], self.size[1]))


def load_data(mongo_conn):

    drawables = []
    layers = mongo_conn.get_database("game").get_collection("layers").find()

    for layer in layers:
        table = mongo_conn.get_database("game").get_collection(layer["_id"])
        for data in table.find():
            drawable = Drawable.decide_object_type(data)

            if drawable is not None:
                drawables.append(drawable)

    return drawables


def run_game():
    mongo_conn = pymongo.MongoClient('localhost', 27017)
    screen = pg.display.set_mode(DISPLAY_MODE)
    pg.display.set_caption(DISPLAY_CAPTION)

    while True:

        for event in pg.event.get():
            if event.type == pg.QUIT:
                sys.exit(0)

        screen.fill(BG_COLOR)

        for drawable in load_data(mongo_conn):
            drawable.draw(screen)
        pg.display.flip()


run_game()

