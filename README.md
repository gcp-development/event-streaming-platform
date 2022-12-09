# Event Streaming Platform (----->Work in progress<-----)

## Motivation

Blockchain and Kafka are complementary, Kafka and the notion of blockchain share many traits, such as immutability, replication, distribution, and the decoupling of applications. This complementary relationship means that we are able to extend the functionality of a given DLT(distributed ledger technology) through sidechain or off-chain activities, such as analytics, integrations with traditional enterprise systems, or even the integration of certain chains and ledgers. 
 
<hr>

## Table of Contents<br>
<ul>
<li><a href="https://github.com/gcp-development/event-streaming-platform/blob/main/README.md#proof-of-conceptkafka" target="_self">Kafka and Kubernetes</a></li>
<li><a href="https://github.com/gcp-development/event-streaming-platform/blob/main/README.md#blockchain" target="_self">Blockchain</a></li> 
<li><a href="https://github.com/gcp-development/event-streaming-platform#consensus-mechanism" target="_self">Consensus Mechanism</a></li>
</ul>
<hr>

### Kafka and Kubernetes

Apache Kafka is frequently deployed on the [Kubernetes](https://kubernetes.io/) management system, which is used to automate deployment, scaling, and operation of containers across clusters of hosts. Cloud-native applications are independent, loosely coupled, and distributed services that deliver high scalability via the cloud. In the same way, the event-driven applications built on Kafka are loosely coupled and designed to scale across a distributed hybrid cloud environment.

![image](https://user-images.githubusercontent.com/76512851/205594178-5de3849d-9e34-4920-ba2b-4e2326469168.png)

By integrating these technologies, this PoC creates a platform for demonstrate blockchain concepts in simple and easy way.

<hr>

### Blockchain

Fundamentally, the data stored in a blockchain must have the following characteristics:
<ul>
 <li>Immutable</li>
 <li>Unhackable</li>
 <li>Persistent</li>
 <li>Distributed</li>
</ul>

![image](https://user-images.githubusercontent.com/76512851/206766875-be6a8c1b-f274-4aa9-8389-78747e4fe1f9.png)

<hr>

### Consensus Mechanism

[Validators](https://github.com/gcp-development/event-streaming-platform/blob/main/basic-blockchain/src/main/java/org/blockchain/Validator.java)

In Proof of Stake blockchains, validators are selected to produce the next block based on their stake. Although often designed with [random functions](https://github.com/gcp-development/event-streaming-platform/blob/main/basic-blockchain/src/main/java/org/blockchain/ParticipantsPool.java#L53) to prevent a front-running consensus, a larger amount staked by a validator could give them a higher chance of producing the next block. Proposed blocks by validators are then propagated to the rest of the set, who verify and add the approved block to the blockchain. 

Proof of Stake(PoS)

In order to participate in PoS, the [validators](https://github.com/gcp-development/event-streaming-platform/blob/main/basic-blockchain/src/main/java/org/blockchain/Validator.java) has to produce some stake. In PoS the validators are selected in terms of some [selection algorithm](https://github.com/gcp-development/event-streaming-platform/blob/main/basic-blockchain/src/main/java/org/blockchain/ParticipantsPool.java#L53) and the amount of stake locked in the network [(ParticipantsPool)](https://github.com/gcp-development/event-streaming-platform/blob/main/basic-blockchain/src/main/java/org/blockchain/ParticipantsPool.java) and only then that [selected validator](https://github.com/gcp-development/event-streaming-platform/blob/main/basic-blockchain/src/main/java/org/blockchain/ParticipantsPool.java#L53) will be allowed to add the [block](https://github.com/gcp-development/event-streaming-platform/blob/main/basic-blockchain/src/main/java/org/blockchain/Block.java). If the validator founds to be malicious, then they will lose all their stake.



<hr>
References:<br>

[Event-Driven](https://martinfowler.com/articles/201701-event-driven.html)<br>
[Benchmarking Apache Kafka: 2 Million Writes Per Second (On Three Cheap Machines)](https://engineering.linkedin.com/kafka/benchmarking-apache-kafka-2-million-writes-second-three-cheap-machines)<br>
[Peercoin Introduction to Proof-of-Stake](https://www.peercoin.net/docs/proof-of-stake)<br>
