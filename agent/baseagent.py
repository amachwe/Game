from abc import ABC, abstractmethod

class BaseAgent(ABC):

    def __init__(self):
        super().__init__()

    @abstractmethod
    def act(self, *args, **kwargs):
        pass

    @abstractmethod
    def save_state(self):
        pass

    @abstractmethod
    def load_state(self):
        pass