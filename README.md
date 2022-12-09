# Event Streaming Platform (Work in progress)

## Motivation

Blockchain and Kafka are complementary, Kafka and the notion of blockchain share many traits, such as immutability, replication, distribution, and the decoupling of applications. This complementary relationship means that we are able to extend the functionality of a given DLT(distributed ledger technology) through sidechain or off-chain activities, such as analytics, integrations with traditional enterprise systems, or even the integration of certain chains and ledgers. 
 
<hr>


![image](https://user-images.githubusercontent.com/76512851/205594178-5de3849d-9e34-4920-ba2b-4e2326469168.png)


### Consensus Mechanism

Proof of Stake(PoS)

In order to participate in PoS, the [validators](https://github.com/gcp-development/event-streaming-platform/blob/main/basic-blockchain/src/main/java/org/blockchain/Validator.java) has to produce some stake. In PoS the validators are selected in terms of some [selection algorithm](https://github.com/gcp-development/event-streaming-platform/blob/main/basic-blockchain/src/main/java/org/blockchain/ParticipantsPool.java#L53) and the amount of stake locked in the network [(ParticipantsPool)](https://github.com/gcp-development/event-streaming-platform/blob/main/basic-blockchain/src/main/java/org/blockchain/ParticipantsPool.java) and only then that validator will be allowed to add the [block](https://github.com/gcp-development/event-streaming-platform/blob/main/basic-blockchain/src/main/java/org/blockchain/Block.java) and the other validators need not waste any computational power since they are not selected. If the validator founds to be malicious, then they will lose their stake.


![image](https://user-images.githubusercontent.com/76512851/206745228-ea99a130-ece3-4217-ac22-b7a0e1bc7798.png)


<hr>
References:<br>

[Event-Driven](https://martinfowler.com/articles/201701-event-driven.html)<br>
[Benchmarking Apache Kafka: 2 Million Writes Per Second (On Three Cheap Machines)](https://engineering.linkedin.com/kafka/benchmarking-apache-kafka-2-million-writes-second-three-cheap-machines)<br>
