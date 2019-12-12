gunicorn app:app --config=config.py

gcloud builds --project YOUR_PROJECT_NAME \
    submit --tag gcr.io/YOUR_PROJECT_NAME/flask-app:v1 .

kubectl apply -f app.yaml

kubectl expose deployment flask-app-tutorial \
    --type=LoadBalancer --port 80 --target-port 8080

kubectl get services -l name=flask-app-tutorial

kubectl scale deployment flask-app-tutorial --replicas=NUMBER
kubectl autoscale deployment flask-app-tutorial \
    --min=NUMBER --max=NUMBER \
    --cpu-ratio=FLOAT --replicas=NUMBER

kubectl set image deployment/flask-app-tutorial \
    flask-app-tutorial=NEW_IMAGE_TAG

echo -n "YOUR_SECRET_TOKEN" | base64


kubectl apply -f secret.yaml

