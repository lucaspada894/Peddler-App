from __future__ import absolute_import
from __future__ import division
from __future__ import print_function

# Define Python imports
import os
import sys
import time
import threading
import math
import logging
import logging.handlers
import re
import random

# Define ML imports
import numpy as np


class Customer():

    def __init__(self, option):
        print(option)