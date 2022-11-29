Kaka setup

```bash
minikube start --driver=docker --cpus 4 --memory 8192
```

![image](https://user-images.githubusercontent.com/76512851/204541847-8ddcda76-3327-4886-856c-eb12d15bfef3.png)


```bash
kubectl apply -f 0_namespace.yml
```

```bash
kubectl get namespace
```

![image](https://user-images.githubusercontent.com/76512851/204543306-bcd63a57-1815-4fee-aff2-d4e3758c2d9c.png)

```bash
kubectl apply -f 2_platform-network.yml
```

```bash
kubectl get NetworkPolicy --namespace=event-streaming-platform
```

![image](https://user-images.githubusercontent.com/76512851/204544713-23490950-612b-4967-9aa2-2b19078809dc.png)

```bash
kubectl apply -f 3_zookeeper-service.yml
```

```bash
kubectl get service --namespace=event-streaming-platform
```

![image](https://user-images.githubusercontent.com/76512851/204545701-3b2d24a6-6ea0-4503-90c0-75a406590837.png)


```bash
kubectl apply -f 4_zookeeper-log-pvc.yml
```

```bash
kubectl apply -f 5_zookeeper-log-pv.yml
```

```bash
kubectl get pv --namespace=event-streaming-platform
```

![image](https://user-images.githubusercontent.com/76512851/204550916-9a71de36-ad25-4809-a25b-4d6f3faf7fbf.png)

```bash
kubectl apply -f 6_zookeeper-data-pvc.yml
```

```bash
kubectl apply -f 7_zookeeper-data-pv.yml
```

![image](https://user-images.githubusercontent.com/76512851/204551817-32a87c16-7920-4b1a-a400-ff57a38d0ac7.png)

```bash
kubectl apply -f 8_zookeeper-deployment.yml
```

```bash
kubectl get deployment --namespace=event-streaming-platform
```

![image](https://user-images.githubusercontent.com/76512851/204552788-143d34b0-a7b7-430c-8269-02c8181ba294.png)


```bash
kubectl get pods --namespace=event-streaming-platform
```

![image](https://user-images.githubusercontent.com/76512851/204553219-b13bdf04-add4-42bf-83e5-26287db4e404.png)

```bash
kubectl logs -f zookeeper-5f66859bc6-dr45b --namespace=event-streaming-platform
```

![image](https://user-images.githubusercontent.com/76512851/204554628-75d72094-0552-4259-aa9d-70b3ee4c87a7.png)

```bash
kubectl apply -f 9_kafka-pvc.yml
```

```bash
kubectl apply -f 10_kafka-pv.yml
```

```bash
kubectl get pv --namespace=event-streaming-platform
```

![image](https://user-images.githubusercontent.com/76512851/204557099-5721f1a5-0de6-4f5c-9fec-06c249facb9b.png)


LoadBalancer

[using minikube tunnel](https://minikube.sigs.k8s.io/docs/handbook/accessing/#using-minikube-tunnel)

```bash
minikube tunnel
```

![image](https://user-images.githubusercontent.com/76512851/204564162-f6d59103-beda-43d1-9332-5edf56620a0f.png)

```bash
kubectl apply -f 11_kafka-deployment.yml
```

