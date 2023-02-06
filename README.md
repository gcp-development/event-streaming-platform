# Event Streaming Platform (Blockchain)

## Motivation

Blockchain and Kafka are complementary, Kafka and the notion of blockchain share many traits, such as immutability, replication, distribution, and the decoupling of applications. This complementary relationship means that we are able to extend the functionality of a given DLT(distributed ledger technology) through sidechain or off-chain activities, such as analytics, integrations with traditional enterprise systems, or even the integration of certain chains and ledgers. 
 
<hr>

## Table of Contents<br>
<ul>
<li><a href="https://github.com/gcp-development/event-streaming-platform#kafka-and-kubernetes" target="_self">Kafka and Kubernetes</a></li>
<li><a href="https://github.com/gcp-development/event-streaming-platform/blob/main/README.md#blockchain" target="_self">Blockchain</a></li> 
<ul>
<li><a href="https://github.com/gcp-development/event-streaming-platform#block-overview" target="_self">Block Overview</a></li>
<li><a href="https://github.com/gcp-development/event-streaming-platform#blockchain-overview" target="_self">Blockchain Overview</a></li>
<li><a href="https://github.com/gcp-development/event-streaming-platform#consensus-mechanism" target="_self">Consensus Mechanism</a></li>
</ul>
<li> <a href="https://github.com/gcp-development/event-streaming-platform#implementation" target="_blank">Implementation</a></li>
 <ul>
  <li><a href="https://github.com/gcp-development/event-streaming-platform#1-run-the-blocks-consumer" target="blank">1) Run the blocks-consumer</a></li>
  <li><a href="https://github.com/gcp-development/event-streaming-platform#2-run-the-block-processor" target="blank">2) Run the block-processor</a></li>
  <li><a href="https://github.com/gcp-development/event-streaming-platform#3-run-the-transactions-producer" target="blank">3) Run the transactions-producer</a></li>
 </ul>
</ul>
<hr>

### Kafka and Kubernetes

Apache Kafka is frequently deployed on the [Kubernetes](https://kubernetes.io/) management system, which is used to automate deployment, scaling, and operation of containers across clusters of hosts. Cloud-native applications are independent, loosely coupled, and distributed services that deliver high scalability via the cloud. In the same way, the event-driven applications built on Kafka are loosely coupled and designed to scale across a distributed hybrid cloud environment.

![image](https://user-images.githubusercontent.com/76512851/205594178-5de3849d-9e34-4920-ba2b-4e2326469168.png)

By integrating these technologies, this PoC creates a platform for demonstrate blockchain concepts in a simple and easy way.

Souce Code:
<ul>
<li><a href="https://github.com/gcp-development/event-streaming-platform/tree/main/kafka-setup" target="blank">kafka-setup</a></li>
</ul>

<hr>

### Blockchain

What is Blockchain?
<ul>
 <li>Blockchain is a transaction record database that is distributed, permission less and maintained around the world by a
network of nodes.</li>
 <li>Blockchain has a distributed group of nodes that oversee the network.</li>
 <li>Blockchain uses decentralized technologies to provide peer-to-peer, permissionless, and immutable network to store
transactions.</li>
</ul>
 
A blockchain network have the following characteristics:
<ul>
 <li>No Central Authority</li>
 <li>Verifiability and Auditability</li>
 <li>Disintermediation</li>
 <li>Confidentiality and Integrity</li>
 <li>Robustness</li>
</ul>

A blockchain network have the following benefits:
<ul>
 <li>No Third-Party Intermediaries</li>
 <li>Greater Transparency</li>
 <li>High Availability</li>
 <li>High Security</li>
 <li>Faster Dealings and Cost Savings</li>
 <li>Improved Traceability</li>
</ul>

#### Block overview

![image](https://user-images.githubusercontent.com/76512851/206841362-e0757f53-a059-4790-a5f3-2d154e04fe34.png)

<ul>
 <li>Block version, version number of the block.</li>
 <li>Previous block hash, the hash of the previous block.</li>
 <li>Merkle tree root hash, Each transaction in the block is
hashed and stored in a tree-like structure such that
  each hash is linked to its parent.</li>
 <li>nBits, encoding of the block target. A block will be
valid only if the hash of its header is below the target
value.</li>
 <li>Nonce, a variable decided by the miner creating the
block changed In order to get a block hash under
target value.</li>
 <li>Timestamp, value is a source of variation for the block
hash.</li>
</ul>

#### Blockchain overview

![image](https://user-images.githubusercontent.com/76512851/206840630-235d178e-b796-456f-8ec7-d85799cff948.png)

<ul>
 <li>Blockchain contains a list of blocks cryptographically connect to the previous block.</li>
 <li>The blocks are connected such that if there is any change in block(n-1) the connection between the next block will
break.</li>
 <li>This makes very hard to change any data in blockchain.</li>
 <li>Each block contains set of transactions data that is linked in the next blocks so that anyone can trace the origin of data.</li>
</ul>

##### Consensus Mechanism

[Validators](https://github.com/gcp-development/event-streaming-platform/blob/main/basic-blockchain/src/main/java/org/blockchain/Validator.java)

In Proof of Stake blockchains, validators are selected to produce the next [block](https://github.com/gcp-development/event-streaming-platform/blob/main/basic-blockchain/src/main/java/org/blockchain/Block.java) based on their stake. Designed with [random functions](https://github.com/gcp-development/event-streaming-platform/blob/main/basic-blockchain/src/main/java/org/blockchain/ParticipantsPool.java#L53) in order to make the selection more fairer,because a larger amount staked by a [validator](https://github.com/gcp-development/event-streaming-platform/blob/main/basic-blockchain/src/main/java/org/blockchain/Validator.java) could give them a higher chance of producing the next [block](https://github.com/gcp-development/event-streaming-platform/blob/main/basic-blockchain/src/main/java/org/blockchain/Block.java). Proposed [blocks](https://github.com/gcp-development/event-streaming-platform/blob/main/basic-blockchain/src/main/java/org/blockchain/Block.java) by [validators](https://github.com/gcp-development/event-streaming-platform/blob/main/basic-blockchain/src/main/java/org/blockchain/Validator.java) are then propagated to the rest of the network, who verify and add the approved [block](https://github.com/gcp-development/event-streaming-platform/blob/main/basic-blockchain/src/main/java/org/blockchain/Block.java) to the blockchain. 

Proof of Stake(PoS)

In order to participate in PoS, the [validators](https://github.com/gcp-development/event-streaming-platform/blob/main/basic-blockchain/src/main/java/org/blockchain/Validator.java) has to produce some stake. In PoS the validators are selected in terms of some [selection algorithm](https://github.com/gcp-development/event-streaming-platform/blob/main/basic-blockchain/src/main/java/org/blockchain/ParticipantsPool.java#L53) and the amount of stake locked in the network [(ParticipantsPool)](https://github.com/gcp-development/event-streaming-platform/blob/main/basic-blockchain/src/main/java/org/blockchain/ParticipantsPool.java) and only then that [selected validator](https://github.com/gcp-development/event-streaming-platform/blob/main/basic-blockchain/src/main/java/org/blockchain/ParticipantsPool.java#L53) will be allowed to add the [block](https://github.com/gcp-development/event-streaming-platform/blob/main/basic-blockchain/src/main/java/org/blockchain/Block.java). If the validator founds to be malicious, then they will lose all their stake.

<hr>

### Implementation

![image](https://user-images.githubusercontent.com/76512851/206766875-be6a8c1b-f274-4aa9-8389-78747e4fe1f9.png)

Transactions Producer

->This [Java project](https://github.com/gcp-development/event-streaming-platform/tree/main/transactions-producer) will create the transactions for our basic blockchain.

Block Processor

->This [Java project](https://github.com/gcp-development/event-streaming-platform/tree/main/blocks-consumer) will process the transactions and create the new blocks for our basic blockchain.

Blocks Consumer

->This [Java project](https://github.com/gcp-development/event-streaming-platform/tree/main/blocks-consumer) will consume the blocks.

Basic Blockchain

->This [Java project](https://github.com/gcp-development/event-streaming-platform/tree/main/basic-blockchain) this library(JAR) contains the objects that form a basic blockchain.

##### 1) Run the blocks-consumer

As a prerequisite the [basic-blockchain project](https://github.com/gcp-development/event-streaming-platform/tree/main/basic-blockchain) needs to create the JAR which the other projects will use.

Open the [blocks-consumer project](https://github.com/gcp-development/event-streaming-platform/tree/main/blocks-consumer) with [Intellij community](https://www.jetbrains.com/idea/download/#section=linux) and run the project.

The listener will subscribe to the blockchain topic to consume any message saved there.

![image](https://user-images.githubusercontent.com/76512851/207286799-293b9ce9-bfb9-4f0f-ae08-07cb32195387.png)

For troubleshooting any issue look at the logs generated by the [log4j](https://logging.apache.org/log4j/2.x/).

![image](https://user-images.githubusercontent.com/76512851/207288001-3ac6ceaf-c615-47de-b4e3-9ff550b64d20.png)

##### 2) Run the block-processor

Open the [block-processor project](https://github.com/gcp-development/event-streaming-platform/tree/main/block-processor) with [Intellij community](https://www.jetbrains.com/idea/download/#section=linux) and run the project.

![image](https://user-images.githubusercontent.com/76512851/207289206-19f61f41-7f73-466a-b62b-52b77075cf96.png)

For troubleshooting any issue look at the logs generated by the [log4j](https://logging.apache.org/log4j/2.x/).

![image](https://user-images.githubusercontent.com/76512851/207289924-e76b7768-19d8-4739-9573-1f248ef8d872.png)

##### 3) Run the transactions-producer

Open the [transactions-producer project](https://github.com/gcp-development/event-streaming-platform/tree/main/transactions-producer) with [Intellij community](https://www.jetbrains.com/idea/download/#section=linux) and run the project.

![image](https://user-images.githubusercontent.com/76512851/207290392-f87e6d60-3e53-4c8c-857c-a1fb294ae0b2.png)

For troubleshooting any issue look at the logs generated by the [log4j](https://logging.apache.org/log4j/2.x/).

![image](https://user-images.githubusercontent.com/76512851/207290846-ebfa1526-c571-4b55-9079-ba2ffdc59b55.png)

The [transactions-producer project](https://github.com/gcp-development/event-streaming-platform/tree/main/transactions-producer) will insert 15 transactions in the transactions topic. This transactions topic is subscribed by the [block-processor project](https://github.com/gcp-development/event-streaming-platform/tree/main/block-processor) and the blocks created inserted in the blockchain topic. 

The run windows should look like this.

![image](https://user-images.githubusercontent.com/76512851/207294741-7f515a90-8667-4f2d-8bfe-9dce451c392a.png)

The [blocks-consumer project](https://github.com/gcp-development/event-streaming-platform/tree/main/blocks-consumer) is subscribed to the blockchain topic which will read any message insert into the blockchain topic.

The run windows should look like this.

![image](https://user-images.githubusercontent.com/76512851/207292859-50ea15d0-22a1-4a8a-8670-714c608aa0a0.png)

The Blockchain created.

![image](https://user-images.githubusercontent.com/76512851/207298415-bddae0e2-e6ab-4ac0-bf0a-d5ad76fdf20c.png)

Source Code:
<ul>
 <li><a href="https://github.com/gcp-development/event-streaming-platform/tree/main/basic-blockchain" target="_blank">basic-blockchain</a></li>
 <li><a href="https://github.com/gcp-development/event-streaming-platform/tree/main/transactions-producer" target="_blank">transactions-producer</a></li>
 <li><a href="https://github.com/gcp-development/event-streaming-platform/tree/main/block-processor" target="_blank">block-processor</a></li>
 <li><a href="https://github.com/gcp-development/event-streaming-platform/tree/main/blocks-consumer" target="_blank">blocks-consumer</a></li>
</ul>

<hr>
References:<br>

[Event-Driven](https://martinfowler.com/articles/201701-event-driven.html)<br>
[Benchmarking Apache Kafka: 2 Million Writes Per Second (On Three Cheap Machines)](https://engineering.linkedin.com/kafka/benchmarking-apache-kafka-2-million-writes-second-three-cheap-machines)<br>
[Peercoin Introduction to Proof-of-Stake](https://www.peercoin.net/docs/proof-of-stake)<br>
