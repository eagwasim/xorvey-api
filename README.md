# xorvey-api

# Deployment
***
docker build -t eagwasim/xorvey-api:0.0.1 .
***
***
docker push eagwasim/xorvey-api:0.0.1
***
kubectl create deployment xorvey-api --image eagwasim/xorvey-api:0.0.1
kubectl run xorvey-api --image eagwasim/xorvey-api:0.0.1 --port 8080
***
kubectl expose deployment xorvey-api --type=NodePort
kubectl expose deployment/xorvey-api --port=8080 --name=xorvey-api-service --type=LoadBalancer
***

# Delete Deployment
*** 
kubectl delete all -l run=xorvey
