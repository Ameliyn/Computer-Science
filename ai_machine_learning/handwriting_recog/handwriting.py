import pandas as pd
import numpy as np
from PIL import Image
from sklearn.model_selection import train_test_split
from os.path import exists

IMAGE_RELATIVE_PATH = "./writein_crops/"


def remove_missing_images(csv):
    for row in csv.iterrows():
        name = IMAGE_RELATIVE_PATH + row[1]["BallotID"] + ".jpg"
        if not exists(name):
            csv.drop(row[0], inplace=True)


def read_and_crop_images(csv, num_images):
    X = np.empty((num_images, 53, 358))
    for i, row in enumerate(csv.iterrows()):
        img = np.array(Image.open(IMAGE_RELATIVE_PATH + row[1]["BallotID"] + ".jpg"))
        X[i, :, :] = img[:53, :358] / 255.
    return X


answers = pd.read_csv("answers.csv")
remove_missing_images(answers)
num_images = answers.shape[0]
X = read_and_crop_images(answers, num_images)

X_train, X_test, y_train, y_test = train_test_split(X, answers, test_size=.2)
X_train, X_validate, y_train, Y_validate = train_test_split(X_train, y_train, test_size=.2)
# train_set, test_set = train_test_split(answers, test_size=.2)
# train_set, validation_set = train_test_split(train_set, test_size=.2)

print(X_train.shape)
print(y_train.shape)

