# Kaka setup

Kafka is a distributed event streaming platform. It’s distributed as Kafka can live on multiple machines. It’s called event streaming as it is processing events (i.e. messages) of something that happened (sent as a key/value pair). The messages are processed as a stream of events, which means that there is no schedule but they can be handled as they arrive.

For this setup its assume that these software are installed and running:
<ul>
  <li><a href="https://docs.docker.com/engine/install/ubuntu/" target="_blank">docker</a></li>
  <li><a href="https://minikube.sigs.k8s.io/docs/start/" target="_blank">minikube</a></li>
  <li><a href="https://kubernetes.io/docs/tasks/tools/install-kubectl-linux/" target="_blank">kubectl</a></li>
</ul>

 Our local development environment is done using minikube to emulate a similar [kubernetes](https://kubernetes.io/) setup. Three images are used for this effect [confluent](https://www.confluent.io/) [cp-zookeeper/Community licensed](https://hub.docker.com/r/confluentinc/cp-zookeeper), [cp-kafka/Community licensed](https://hub.docker.com/r/confluentinc/cp-kafka) and [cp-schema-registry/Community licensed](https://hub.docker.com/r/confluentinc/cp-schema-registry). The image cp-zookeeper will create a single node [(Zookeeper)](https://zookeeper.apache.org/), the image cp-kafka will create a single broker cluster [(Kafka)](https://kafka.apache.org/) and the image cp-schema-registry will create the [schema registry](https://docs.confluent.io/platform/current/schema-registry/index.html#sr-overview).

### Table of Contents
<ul>
  <li><a href="https://github.com/gcp-development/event-streaming-platform/blob/main/kafka-setup/README.md#minikube" target="_blank">Minikube</a></li>
  <li><a href="" target="_blank">Namespace</a></li>
  <li><a href="https://github.com/gcp-development/event-streaming-platform/blob/main/kafka-setup/README.md#network" target="_blank">Network</a></li>
  <li><a href="https://github.com/gcp-development/event-streaming-platform/blob/main/kafka-setup/README.md#zookeeper" target="_blank">Zookeeper</a></li>
  <li><a href="https://github.com/gcp-development/event-streaming-platform/blob/main/kafka-setup/README.md#kafka" target="_blank">Kafka</a></li>
  <li><a href="https://github.com/gcp-development/event-streaming-platform/blob/main/kafka-setup/README.md#load-balancerkafka" target="_blank">Load Balancer(kafka)</a></li>
  <li><a href="https://github.com/gcp-development/event-streaming-platform/blob/main/kafka-setup/README.md#schema-registry" target="_blank">Schema Registry</a></li>
    <li><a href="https://github.com/gcp-development/event-streaming-platform/blob/main/kafka-setup/README.md#load-balancerschema-registry-service" target="_blank">Load Balancer(schema-registry-service)</a></li>
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

![image](https://user-images.githubusercontent.com/76512851/205451368-a73d2188-e725-42ac-a8d6-564fbab12e25.png)

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

![image](https://user-images.githubusercontent.com/76512851/205451564-eb6728c3-7c45-4756-876b-6df4c96eb957.png)

Verify the pod log.

```bash
kubectl logs -f zookeeper-78897b79bc-sd7tf --namespace=event-streaming-platform
```

![image](https://user-images.githubusercontent.com/76512851/204554628-75d72094-0552-4259-aa9d-70b3ee4c87a7.png)

<hr>

### Kafka

Create the kafka [service](https://kubernetes.io/docs/concepts/services-networking/service/).

```bash
kubectl apply -f 9_kafka-service.yml
```

```bash
kubectl get service --namespace=event-streaming-platform
```

![image](https://user-images.githubusercontent.com/76512851/205451774-b9b9b58e-c385-4548-906a-83c23282a7b8.png)

Create a persistent [storage](https://kubernetes.io/docs/concepts/storage/persistent-volumes/) for kafka.

PersistentVolumeClaim (PVC)

```bash
kubectl apply -f 10_kafka-pvc.yml
```

PersistentVolume (PV)

```bash
kubectl apply -f 11_kafka-pv.yml
```

```bash
kubectl get pv --namespace=event-streaming-platform
```

![image](https://user-images.githubusercontent.com/76512851/204557099-5721f1a5-0de6-4f5c-9fec-06c249facb9b.png)

Create the [deployment](https://kubernetes.io/docs/concepts/workloads/controllers/deployment/) for kafka.

```bash
kubectl apply -f 12_kafka-deployment.yml
```

```bash
kubectl get deployment --namespace=event-streaming-platform
```

![image](https://user-images.githubusercontent.com/76512851/204644836-37b993e9-db0d-415c-b9e9-ed3ae11fc203.png)

Verify the [pod](https://kubernetes.io/docs/concepts/workloads/pods/) created.

```bash
kubectl get pod --namespace=event-streaming-platform
```

![image](https://user-images.githubusercontent.com/76512851/205452572-b3cd7249-2965-42a7-abe1-4830cd4e3e80.png)


Verify the pod log.

```bash
kubectl logs -f kafka-7dc9b87d74-5cqkj --namespace=event-streaming-platform
```

![image](https://user-images.githubusercontent.com/76512851/204647958-c8e88a17-c261-4d0f-9426-9f8e8e9dc218.png)

Login into the pod.

```bash
kubectl exec -it kafka-7dc9b87d74-5cqkj --namespace=event-streaming-platform -- /bin/bash
```

![image](https://user-images.githubusercontent.com/76512851/205452931-1716b2b6-8ee0-4aa0-8a7b-5163b15fae8f.png)

Create a [compacted topic](https://developer.confluent.io/learn-kafka/architecture/compaction/).

```bash
kafka-topics --create --bootstrap-server kafka:29092 --replication-factor 1 --partitions 1 --topic test-topic
```

![image](https://user-images.githubusercontent.com/76512851/204649334-d8e8d31f-1558-455f-a938-29e13db348a6.png)

Verify the topic created.

```bash
kafka-topics --describe --topic test-topic --bootstrap-server kafka:29092
```

![image](https://user-images.githubusercontent.com/76512851/204649653-4e4bb02a-b3f4-4246-83e2-99d2d7834a31.png)


Create a couple of messages.

```bash
kafka-console-producer --broker-list kafka:29092 --topic test-topic --property parse.key=true --property key.separator=,
```

![image](https://user-images.githubusercontent.com/76512851/204650040-8c77f6aa-242b-4401-96a1-738a8471d40d.png)

Read the topic messages.

```bash
kafka-console-consumer --topic test-topic --from-beginning --bootstrap-server kafka:29092 --property parse.key=true --property key.separator=,
```

![image](https://user-images.githubusercontent.com/76512851/204650853-951276c6-a154-4240-b728-3073e35cf36e.png)

<hr>

### Load Balancer(kafka)

Open a new terminal window on Ubuntu (Ctrl+Alt+T) and execute the [minikube tunnel](https://minikube.sigs.k8s.io/docs/handbook/accessing/#using-minikube-tunnel).

```bash
minikube tunnel
```
Note:The minikube tunel is used to simulate a [cloud load balancer](https://kubernetes.io/docs/tasks/access-application-cluster/create-external-load-balancer/) that sends traffic to the correct port on our pods in kubernetes. For [troubleshooting](https://minikube.sigs.k8s.io/docs/handbook/troubleshooting/) execute the "minikube start --alsologtostderr --v=2".

![image](https://user-images.githubusercontent.com/76512851/204644421-4b5c0dcf-2d42-45e5-b270-41fda55aa395.png)

Open a new terminal window on Ubuntu (Ctrl+Alt+T) and execute.<br>
Create the [load balancer](https://kubernetes.io/docs/concepts/services-networking/service/#loadbalancer) for the kafka [service](https://kubernetes.io/docs/concepts/services-networking/service/).

```bash
kubectl apply -f 13_kafka-service-load-balancer.yml
```

```bash
kubectl get svc --namespace=event-streaming-platform
```

![image](https://user-images.githubusercontent.com/76512851/205453952-0ff5b6f5-b5e7-4fa5-bda6-80dbdcd7e64c.png)

![image](https://user-images.githubusercontent.com/76512851/204646717-b1c85ef1-af1b-44f5-a30c-9bfdc16e927e.png)


```bash
ping 10.104.90.93
```

![image](https://user-images.githubusercontent.com/76512851/205454184-b82c9b58-7f34-4ba4-a243-ea1a5089c98a.png)

Add kafka to our /etc/hosts file in the host machine in order to connect kafka clients to the kafka broker via the load balancer created.

```bash
sudo nano /etc/hosts
```

![image](https://user-images.githubusercontent.com/76512851/205455744-7833c8b1-1113-4e8f-ad43-e7a26601e0f1.png)

### Schema Registry

Create a service for Schema Registry.

```bash
kubectl apply -f 14_schema-registry-service.yml 
```

Verify the service.

```bash
 kubectl get service --namespace=event-streaming-platform
```

![image](https://user-images.githubusercontent.com/76512851/205454831-7f1f2a17-169d-448f-9275-5bd4fc1a3260.png)

Create the deployment for Schema Registry.

```bash
kubectl apply -f 15_schema-registry-deployment.yml
```

Verify the pod.

```bash
 kubectl get pod --namespace=event-streaming-platform
```

![image](https://user-images.githubusercontent.com/76512851/205455395-9fb1e767-2198-48ce-8ceb-ba3c1336d888.png)

Verify the pod log.

```bash
 kubectl logs -f schema-registry-6fd49bdfb4-klhzv --namespace=event-streaming-platform
```

![image](https://user-images.githubusercontent.com/76512851/205455253-8f71c3af-6a55-4f78-b5b6-afbc6b5c9bfa.png)

Note:this can be found in the end of the log

<hr>

### Load Balancer(schema-registry-service)

Create the load balancer for the schema-registry service.

```bash
kubectl apply -f 16_schema-registry-service-load-balancer.yml
```

```bash
kubectl get svc --namespace=event-streaming-platform
```

![image](https://user-images.githubusercontent.com/76512851/205455766-29ddd41e-38b0-4515-818b-a4395b151016.png)

![image](https://user-images.githubusercontent.com/76512851/205455823-5c4e0d05-ae3a-4845-80b3-7d8465d41e9e.png)

![image](https://user-images.githubusercontent.com/76512851/205456398-dd5b255f-e289-40ab-b9f8-158dd9082b99.png)

<hr>
References:<br>

[Apache Kafka](https://kafka.apache.org/documentation/#gettingStarted)<br>
[Services, Load Balancing, and Networking](https://kubernetes.io/docs/concepts/services-networking/)<br>
