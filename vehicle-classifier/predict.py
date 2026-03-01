import cv2
import numpy as np
from tensorflow.keras.models import load_model

# Load model (correct name)
model = load_model("vehicle_classifier.h5")

# Load your test image
img = cv2.imread("test.jpg")
img = cv2.resize(img, (224, 224))
img = img / 255.0
img = np.expand_dims(img, axis=0)

# Class names (match images folder)
class_names = ['bicycle', 'car', 'motor bike', 'truck']

# Predict
prediction = model.predict(img)
class_index = np.argmax(prediction)
print("Predicted:", class_names[class_index])
