import cv2

# Load pre-trained vehicle detection cascade


cascade = cv2.CascadeClassifier('haarcascade_car.xml')

#cascade1 = cv2.CascadeClassifier('bicycle.xml')

# Open video stream or video file
video_path = 'vehicles1.mp4'  
cap = cv2.VideoCapture(video_path)

count = 0


while  True: 
    ret, frame = cap.read()

    if not ret:
        break

    # Convert frame to grayscale for vehicle detection
    gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)

    # Detect vehicles in the frame
    vehicles = cascade.detectMultiScale(gray, scaleFactor=1.1, minNeighbors=5, minSize=(30, 30))

    # Draw bounding boxes around the detected vehicles
    for (x, y, w, h) in vehicles:
        cv2.rectangle(frame, (x, y), (x + w, y + h), (0, 255, 0), 2)

        count += 1
        
    # Display the frame with vehicle detections
    cv2.imshow('Vehicle Detection', frame)

    if cv2.waitKey(1) == ord('q'):
        break

# Release the video capture and close windows
cap.release()
cv2.destroyAllWindows()

print("number of cars",count)