import sys
import argparse

sys.path.insert(0, './app')

from Customer import Customer

if __name__ == '__main__':

    # Set parser
    parser = argparse.ArgumentParser()

    ## PATH TO FILE FROM BASE ##
    parser.add_argument('--id', type=str, help='id of user to be added to srtipe', required=True)

    option = parser.parse_args()

    m = Customer(option)