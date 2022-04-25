import pandas as pd
import matplotlib.pyplot as plt
import numpy as np
import tensorflow as tf
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


def display_history(history):
    pd.DataFrame(history.history).plot(figsize=(8, 5))
    plt.grid(True)
    plt.gca().set_ylim(0, 1)  # set the vertical range to [0-1]
    plt.show()


answers = pd.read_csv("answers.csv")
# This is for testing purposes to use only 1000 images
# answers = pd.read_csv("answers.csv").sample(n=1000)
remove_missing_images(answers)
num_images = answers.shape[0]
X = read_and_crop_images(answers, num_images)

y = answers.drop(columns=["BallotID"])
# Split dataset into training, testing, and validation sets
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=.2)
X_train, X_validate, y_train, y_validate = train_test_split(X_train, y_train, test_size=.2)


print(X_train.shape)
print(y_train.shape)


# model = keras.models.Sequential([
#  keras.layers.Flatten(input_shape=[28, 28]),
#  keras.layers.Dense(300, activation="relu"),
#  keras.layers.Dense(100, activation="sigmoid"),
#  keras.layers.Dense(10, activation="softmax")
# ])

# history = model.fit(X_train, y_train, epochs=30, validation_data=(X_valid, y_valid))

# model = tf.keras.models.Sequential([tf.keras.layers.Dense(1, activation="sigmoid")])
# history = model.fit(X_train, y_train, epochs=10, validation_data=(X_validate, y_validate))
#
# display_history(history)

# history = model.fit(X_train, y_train, epochs=30, validation_data=(X_valid, y_valid))
################################
model = tf.keras.models.Sequential([
    tf.keras.layers.Flatten(input_shape=[53, 358]),
    tf.keras.layers.Dense(10, activation="sigmoid"),
    tf.keras.layers.Dense(10, activation="softmax")])
model.compile(loss="mean_squared_error", optimizer="sgd", metrics=["accuracy"])
history = model.fit(X_train, y_train, epochs=10, validation_data=(X_validate, y_validate))

display_history(history)
