'''
Trains a simple LeNet-5 (http://yann.lecun.com/exdb/lenet/) adapted to the HOMUS dataset using Keras Software (http://keras.io/)

LeNet-5 demo example http://eblearn.sourceforge.net/beginner_tutorial2_train.html

This example executed with 8x8 reescaled images and 50 epochs obtains an accuracy close to 32%.
'''

from __future__ import print_function
from PIL import Image, ImageOps
import numpy as np
import glob

from keras.models import Sequential
from keras.layers import Dense, Dropout, Activation, Flatten
from keras.layers import Convolution2D, MaxPooling2D
from keras.utils import np_utils
from keras.optimizers import SGD, adam, adadelta, nadam
from keras.models import load_model
from sklearn.cross_validation import StratifiedKFold
from keras import backend as K
from keras.preprocessing.image import ImageDataGenerator

do_cv = True

batch_size = 256
nb_classes = 32
nb_epoch = 25

# HOMUS contains images of 40 x 40 pixels
# input image dimensions for train 
img_rows, img_cols = 30, 30

# number of convolutional filters to use
nb_filters1 = 8 #6 #8
nb_filters2 = 20 #16 20
nb_filters3 = 120


# convolution kernel size
nb_conv1 = 5 #5
nb_conv2 = 6
nb_conv3 = 2


# size of pooling area for max pooling
nb_pool = 2

accuracy_sum = 0
	


#
# Load data from data/HOMUS/train_0, data/HOMUS/train_1,...,data/HOMUS_31 folders from HOMUS images
#
def load_data():
	image_list = []
	class_list = []
	for current_class_number in range(0,nb_classes):	# Number of class
		for filename in glob.glob('./data/HOMUS/train_{}/*.jpg'.format(current_class_number)):
			im=Image.open(filename).resize((img_rows,img_cols)).convert('L')
			im=ImageOps.invert(im)	# Meaning of grey level is 255 (black) and 0 (white)
			#im.show()
			image_list.append(np.asarray(im).astype('float32')/255)
			class_list.append(current_class_number)

	n = len(image_list)	# Total examples			

	if K.image_dim_ordering() == 'th':
		X = np.asarray(image_list).reshape(n,1,img_rows,img_cols)
		input_shape = (1, img_rows, img_cols)
	else:
		X = np.asarray(image_list).reshape(n,img_rows,img_cols,1)
		input_shape = (img_rows, img_cols, 1)

	Y = np.asarray(class_list)

	return X, Y, input_shape, n

def train_and_evaluate(model, data_train, labels_train, data_test, labels_test):
	model.fit(data_train, labels_train, batch_size=batch_size, nb_epoch=nb_epoch,
          verbose=1, validation_data=(data_test, labels_test))
	score = model.evaluate(data_test, labels_test, verbose=0)
	#
	# Results
	#
	print('Test score:', score[0])
	print('Test accuracy:', score[1])
	return score[1]

def train_and_evaluate_augmentation(model, data_train, labels_train, data_test, labels_test):
	datagen = ImageDataGenerator(
    	rotation_range=15,
    	width_shift_range=0.2,
    	height_shift_range=0.2)

	datagen.fit(data_train)

	# fits the model on batches with real-time data augmentation:
	model.fit_generator(datagen.flow(data_train, labels_train, batch_size=32),
		samples_per_epoch=len(data_train), nb_epoch=nb_epoch, verbose=1)
	score = model.evaluate(data_test, labels_test, verbose=0)
	#
	# Results
	#
	print('Test score:', score[0])
	print('Test accuracy:', score[1])
	return score[1]

#
# Neural Network Structure definition
#
def create_model(input_shape):
	model = Sequential()

	model.add(Convolution2D(nb_filters1, nb_conv1, nb_conv1, border_mode='valid', input_shape = input_shape))
	model.add(MaxPooling2D(pool_size=(nb_pool, nb_pool)))
	model.add(Activation("relu"))
	#model.add(Dropout(0.25))


	model.add(Convolution2D(nb_filters2, nb_conv2, nb_conv2, border_mode='valid'))
	model.add(MaxPooling2D(pool_size=(nb_pool, nb_pool)))
	model.add(Activation("relu"))
	#model.add(Dropout(0.35))
	model.add(Dropout(0.1))

	model.add(Convolution2D(nb_filters3, nb_conv3, nb_conv3, border_mode='valid'))
	model.add(Dropout(0.35))

	model.add(Flatten())
	model.add(Dense(256))
	model.add(Activation("relu"))
	model.add(Dense(nb_classes))
	model.add(Activation('softmax'))

	optimizer=adam()
	model.compile(loss='categorical_crossentropy',optimizer=optimizer, metrics=['accuracy'])

	return model



X, Y, input_shape, n = load_data()

if do_cv:
	print("----------------------------");
	print("-- DOING CROSS VALIDATION --");
	print("----------------------------");

	n_folds = 10
	skf = StratifiedKFold(Y, n_folds=n_folds, shuffle=False)
	Y = np_utils.to_categorical(Y, nb_classes)
	for i, (train, test) in enumerate(skf):
		model = None # Clearing the NN.
		model = create_model(input_shape)

		print("----------------------------");
		print("Running Fold", i+1, "/", n_folds)
		print("----------------------------");
		accuracy_sum += train_and_evaluate(model, X[train], Y[train], X[test], Y[test])

	print("AVG Test Accuracy: ", accuracy_sum/10.0)

else:
	Y = np_utils.to_categorical(Y, nb_classes)
	# Shuffle (X,Y)
	randomize = np.arange(len(Y))
	np.random.shuffle(randomize)
	X, Y = X[randomize], Y[randomize]
	n_partition = int(n*0.9)	# Train 90% and Test 10%
	X_train = X[:n_partition]
	Y_train = Y[:n_partition]

	X_test  = X[n_partition:]
	Y_test  = Y[n_partition:]

	print(X_train.shape, 'train samples')
	print(X_test.shape, 'test samples')
	print(input_shape,'input_shape')
	print(nb_epoch,'epochs')
	
	# the data split between train and test sets
	n_epochs = 32
	
	#datagen.fit(X_train)
	model = create_model(input_shape)

	#entrenamiento con valores normales
	#train_and_evaluate(model, X_train, Y_train, X_test, Y_test)
	train_and_evaluate_augmentation(model, X_train, Y_train, X_test, Y_test)


# file name to save model
filename='homus_cnn.h5'

# save network model
model.save(filename)

# load neetwork model
#model = load_model(filename)

