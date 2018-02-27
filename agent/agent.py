from random import Random
from matplotlib import pyplot as plt
import math
from baseagent import BaseAgent


class RandomAgent(BaseAgent):

    DIRECTION = [
        [(1, 1), "NE"],
        [(1, 0), "E"],
        [(1, -1), "SE"],
        [(0, -1), "S"],
        [(-1, -1), "SW"],
        [(-1, 0), "W"],
        [(-1, 1), "NW"],
        [(0, 1), "N"]
    ]

    DIRECTIONS = 8

    rnd = Random()

    def __init__(self, id, power=100, init_pos=(0,0)):
        self.X = init_pos[0]
        self.Y = init_pos[1]
        self.id = id
        self.power = power
        self.alive = True
        self._X = []
        self._Y = []

    @classmethod
    def step_drive(cls, agents, *args, **kwargs):

        for agent in agents:
            agent.act(args, agents=agents, resource_threshold=kwargs["resource_threshold"])

    def act(self, *args, **kwargs):

        if not self.alive:
            return

        for agent in self.sense(kwargs["agents"]):
            if agent.power > self.power:
                agent.power += self.power
                self.alive = False
                print("Agent " + str(self.id) + " killed by "+str(agent.id))
                return

        # Random movement
        move = RandomAgent.DIRECTION[RandomAgent.rnd.randint(0, RandomAgent.DIRECTIONS-1)][0]

        if kwargs["resource_threshold"] > RandomAgent.rnd.random():
            self._X.append(self.X)
            self._Y.append(self.Y)
            self.X = self.X + move[0]
            self.Y = self.Y + move[1]
            self.power -= 1

            if self.power <= 0:
                self.alive = False
                print("Agent " + str(self.id) + " is dead.")
                return

        else:
            self.power += 1

    def sense(self, agents):
        local_agents = []
        for agent in agents:
            if agent.id != self.id:
                distance = math.sqrt(((agent.X - self.X)**2) + ((agent.Y - self.Y)**2))

                if distance <= 1:
                    local_agents.append(agent)

        return local_agents

    def save_state(self):
        pass

    def load_state(self):
        pass



if __name__ == '__main__':

    # Tests

    a = RandomAgent(1, init_pos=(2,2))
    b = RandomAgent(2)
    c = RandomAgent(3, init_pos=(-2,-2))

    for i in range(0, 10000):
        RandomAgent.step_drive([a, b, c], resource_threshold=0.3)

    print(a.power,b.power,c.power)
    plt.plot(a._X, a._Y)
    plt.plot(b._X, b._Y)
    plt.plot(c._X, c._Y)
    plt.show()


