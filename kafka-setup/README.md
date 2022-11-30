# Kaka setup

Kafka is a distributed event streaming platform. It’s distributed as Kafka can live on multiple machines. It’s called event streaming as it is processing events (i.e. messages) of something that happened (sent as a key/value pair). The messages are processed as a stream of events, which means that there is no schedule but they can be handled as they arrive.

For this setup its assume that these software are installed and running:
<ul>
  <li><a href="https://docs.docker.com/engine/install/ubuntu/" target="_blank">docker</a></li>
  <li><a href="https://minikube.sigs.k8s.io/docs/start/" target="_blank">minikube</a></li>
  <li><a href="https://kubernetes.io/docs/tasks/tools/install-kubectl-linux/" target="_blank">kubectl</a></li>
</ul>

 Our local development environment is done using minikube to emulate a similar [kubernetes](https://kubernetes.io/) setup. Two images are used for this effect [confluent](https://www.confluent.io/) [cp-zookeeper/Community licensed](https://hub.docker.com/r/confluentinc/cp-zookeeper) and [cp-kafka/Community licensed](https://hub.docker.com/r/confluentinc/cp-kafka). The image cp-zookeeper will create a single node [(Zookeeper)](https://zookeeper.apache.org/) and the image cp-kafka will create a single broker cluster [(Kafka)](https://kafka.apache.org/).

### Table of Contents
<ul>
  <li><a href="https://github.com/gcp-development/event-streaming-platform/blob/main/kafka-setup/README.md#minikube" target="_blank">Minikube</a></li>
  <li><a href="" target="_blank">Namespace</a></li>
  <li><a href="https://github.com/gcp-development/event-streaming-platform/blob/main/kafka-setup/README.md#network" target="_blank">Network</a></li>
  <li><a href="https://github.com/gcp-development/event-streaming-platform/blob/main/kafka-setup/README.md#zookeeper" target="_blank">Zookeeper</a></li>
  <li><a href="https://github.com/gcp-development/event-streaming-platform/blob/main/kafka-setup/README.md#kafka" target="_blank">Kafka</a></li>
  <li><a href="https://github.com/gcp-development/event-streaming-platform/blob/main/kafka-setup/README.md#loadbalancer" target="_blank">LoadBalancer</a></li>
</ul>

<hr>

### Minikube

Start the minikube cluster.

```bash
minikube start --driver=docker --cpus 4 --memory 8192
```

![image](https://user-images.githubusercontent.com/76512851/204541847-8ddcda76-3327-4886-856c-eb12d15bfef3.png)

<hr>

### Namespace

Create a [namespace](https://kubernetes.io/docs/tasks/administer-cluster/namespaces-walkthrough/) for our resources.

```bash
kubectl apply -f 1_namespace.yml
```

```bash
kubectl get namespace
```

![image](https://user-images.githubusercontent.com/76512851/204543306-bcd63a57-1815-4fee-aff2-d4e3758c2d9c.png)

<hr>

### Network

Create a [network](https://kubernetes.io/docs/concepts/services-networking/network-policies/) for our resources.

```bash
kubectl apply -f 2_platform-network.yml
```

```bash
kubectl get NetworkPolicy --namespace=event-streaming-platform
```

![image](https://user-images.githubusercontent.com/76512851/204544713-23490950-612b-4967-9aa2-2b19078809dc.png)

<hr>

### Zookeeper

Create a [service](https://kubernetes.io/docs/concepts/services-networking/service/) for zookepper.

```bash
kubectl apply -f 3_zookeeper-service.yml
```

```bash
kubectl get service --namespace=event-streaming-platform
```

![image](https://user-images.githubusercontent.com/76512851/204641753-a22bf4bc-7f03-480f-b9f4-dbd75defa5fb.png)

Create a persistent [storage](https://kubernetes.io/docs/concepts/storage/persistent-volumes/) for the zookepper log.

PersistentVolumeClaim (PVC)

```bash
kubectl apply -f 4_zookeeper-log-pvc.yml
```

PersistentVolume (PV)

```bash
kubectl apply -f 5_zookeeper-log-pv.yml
```
```bash
kubectl get pv --namespace=event-streaming-platform
```

![image](https://user-images.githubusercontent.com/76512851/204550916-9a71de36-ad25-4809-a25b-4d6f3faf7fbf.png)

Create a persistent [storage](https://kubernetes.io/docs/concepts/storage/persistent-volumes/) for the zookepper data.

PersistentVolumeClaim (PVC)

```bash
kubectl apply -f 6_zookeeper-data-pvc.yml
```

PersistentVolume (PV)

```bash
kubectl apply -f 7_zookeeper-data-pv.yml
```

```bash
kubectl get pv --namespace=event-streaming-platform
```

![image](https://user-images.githubusercontent.com/76512851/204551817-32a87c16-7920-4b1a-a400-ff57a38d0ac7.png)

Create the [deployment](https://kubernetes.io/docs/concepts/workloads/controllers/deployment/) for the zookeeper.

```bash
kubectl apply -f 8_zookeeper-deployment.yml
```

```bash
kubectl get deployment --namespace=event-streaming-platform
```

![image](https://user-images.githubusercontent.com/76512851/204552788-143d34b0-a7b7-430c-8269-02c8181ba294.png)

Verify the [pod](https://kubernetes.io/docs/concepts/workloads/pods/) created.

```bash
kubectl get pods --namespace=event-streaming-platform
```

![image](https://user-images.githubusercontent.com/76512851/204553219-b13bdf04-add4-42bf-83e5-26287db4e404.png)

Verify the pod log.

```bash
kubectl logs -f zookeeper-5f66859bc6-dr45b --namespace=event-streaming-platform
```

![image](https://user-images.githubusercontent.com/76512851/204554628-75d72094-0552-4259-aa9d-70b3ee4c87a7.png)

<hr>

### Kafka

Create a persistent [storage](https://kubernetes.io/docs/concepts/storage/persistent-volumes/) for kafka.

PersistentVolumeClaim (PVC)

```bash
kubectl apply -f 9_kafka-pvc.yml
```

PersistentVolume (PV)

```bash
kubectl apply -f 10_kafka-pv.yml
```

```bash
kubectl get pv --namespace=event-streaming-platform
```

![image](https://user-images.githubusercontent.com/76512851/204557099-5721f1a5-0de6-4f5c-9fec-06c249facb9b.png)

<hr>

### Load Balancer

Open a new terminal window on Ubuntu (Ctrl+Alt+T) and execute the [minikube tunnel](https://minikube.sigs.k8s.io/docs/handbook/accessing/#using-minikube-tunnel). The minikube tunel is used to simulate a [cloud load balancer](https://kubernetes.io/docs/tasks/access-application-cluster/create-external-load-balancer/).

```bash
minikube tunnel
```

![image](https://user-images.githubusercontent.com/76512851/204644421-4b5c0dcf-2d42-45e5-b270-41fda55aa395.png)

Open a new terminal window on Ubuntu (Ctrl+Alt+T) and execute

```bash
kubectl apply -f 11_kafka-deployment.yml
```

```bash
kubectl get deployment --namespace=event-streaming-platform
```

![image](https://user-images.githubusercontent.com/76512851/204644836-37b993e9-db0d-415c-b9e9-ed3ae11fc203.png)

```bash
kubectl apply -f 12_kafka-service--nodeport.yml
```

```bash
kubectl apply -f 13_kafka-service-load-balancer.yml
```

```bash
kubectl get service --namespace=event-streaming-platform
```

![image](https://user-images.githubusercontent.com/76512851/204646433-2cd21921-a753-4326-b8eb-6f13fddb349d.png)


![image](https://user-images.githubusercontent.com/76512851/204646717-b1c85ef1-af1b-44f5-a30c-9bfdc16e927e.png)


![image](https://user-images.githubusercontent.com/76512851/204655281-ea98d4e3-881b-4da9-9163-149932fc2915.png)

```bash
kubectl get pod --namespace=event-streaming-platform
```

![image](https://user-images.githubusercontent.com/76512851/204647594-c0e26cd1-040f-4c9b-a18d-158e5bd64802.png)

```bash
kubectl logs -f kafka-7dc9b87d74-pkmhk --namespace=event-streaming-platform
```

![image](https://user-images.githubusercontent.com/76512851/204647958-c8e88a17-c261-4d0f-9426-9f8e8e9dc218.png)


```bash
kubectl exec -it kafka-7dc9b87d74-pkmhk --namespace=event-streaming-platform -- /bin/bash
```

```bash
kafka-topics --create --bootstrap-server kafka:29092 --replication-factor 1 --partitions 1 --topic test-topic
```

![image](https://user-images.githubusercontent.com/76512851/204649334-d8e8d31f-1558-455f-a938-29e13db348a6.png)

```bash
kafka-topics --describe --topic test-topic --bootstrap-server kafka:29092
```

![image](https://user-images.githubusercontent.com/76512851/204649653-4e4bb02a-b3f4-4246-83e2-99d2d7834a31.png)

```bash
kafka-console-producer --broker-list kafka:29092 --topic test-topic --property parse.key=true --property key.separator=,
```

![image](https://user-images.githubusercontent.com/76512851/204650040-8c77f6aa-242b-4401-96a1-738a8471d40d.png)

```bash
kafka-console-consumer --topic test-topic --from-beginning --bootstrap-server kafka:29092 --property parse.key=true --property key.separator=,
```

![image](https://user-images.githubusercontent.com/76512851/204650853-951276c6-a154-4240-b728-3073e35cf36e.png)

```bash
sudo nano /etc/hosts
```

![image](https://user-images.githubusercontent.com/76512851/204654808-ace4689a-a9fd-4533-b457-89730afc3b2b.png)

<hr>
References:<br>

[Apache Kafka](https://kafka.apache.org/documentation/#gettingStarted)<br>
[Topic Compaction](https://developer.confluent.io/learn-kafka/architecture/compaction/)<br>
[Type Nodeport](https://kubernetes.io/docs/concepts/services-networking/service/#type-nodeport)<br>
[Type LoadBalancer](https://kubernetes.io/docs/concepts/services-networking/service/#loadbalancer)<br>
