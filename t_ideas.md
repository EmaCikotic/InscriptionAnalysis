# Possible Research Direction

## What Seems Underexplored

Most existing work on Bitcoin inscriptions focuses on how inscriptions affect Bitcoin itself, for example:

- Transaction fees
- Block space usage
- Miner revenue
- Network activity
- Protocol adoption

Much less attention appears to be given to the actual **contents of inscriptions**.

Possible research questions include:

- What kinds of data are people storing?
- Which protocols dominate?
- How repetitive is the content?
- How does inscription content evolve over time?
- How much storage is consumed by different content types?
- Which new protocols appear over time?

This is the direction that the current project has started exploring.

---

# Possible Research Contributions

## Content-Based Analysis

### Protocol Evolution

Instead of reporting only the overall proportions, for example:

```text
Namespace: 52%
Bitmap: 29%
JSON: 3%
```

analyze how protocol usage changes over time:

```text
Month        Namespace   Bitmap   JSON   HTML
2023-02         ...
2023-03         ...
2023-04         ...
```

Possible questions:

- When did Bitmap become popular?
- How did JSON usage change over time?
- Which protocols appeared first?
- Which protocols dominate the months with unusually high activity?
- Are protocol shifts associated with major inscription campaigns?

---

### Storage Analysis

Rather than only counting inscriptions, analyze the blockchain storage consumed by different content types.

Possible questions:

- How much space is occupied by HTML inscriptions?
- How much space is occupied by JSON inscriptions?
- How much space is occupied by Bitmap identifiers?
- Which content types consume the largest amount of blockchain storage?
- Which content types are the most storage-efficient?

---

### Duplicate Content Analysis

The current project already measures duplicate inscriptions.

Possible extensions include:

- Which protocols generate the most duplicate content?
- Which contents are duplicated most frequently?
- Are duplicate campaigns concentrated within specific time periods?
- Are duplicate inscriptions associated with particular protocols?

---

## Graph-Based Analysis

A natural extension is to represent inscription relationships as graphs.

### Detecting Coordinated Behaviour

Suppose thousands of nearly identical inscriptions appear within a short period.

Construct a graph where:

- **Node** = inscription (or address)
- **Edge** represents a relationship such as:
    - identical content
    - highly similar content
    - reference between inscriptions
    - same creator
    - creation within a short time interval

Graph algorithms could include:

- Connected components
- Community detection
- Centrality measures
- Clustering
- Density analysis

Possible research question:

> Can graph-based methods identify coordinated inscription campaigns?

This would combine content analysis with graph theory and cybersecurity.

---

### Blockchain Forensics Graph

If additional blockchain data is collected, the graph could include:

- Wallets
- Transactions
- Inscriptions
- URLs
- Domains

Example:

```text
Wallet ── creates ──> Inscription
   │
   ├── sends BTC to ──> Wallet
   │
   └── contains ──> URL / Domain
```

Possible questions:

- Which wallets create the largest number of inscriptions?
- Are groups of wallets cooperating?
- Can suspicious clusters be identified?
- Are certain URLs repeatedly associated with the same wallets?

This moves the project toward blockchain forensics.

---

## Cybersecurity-Oriented Analysis

The inscription contents themselves can also be analyzed for security-relevant information.

Possible indicators include:

- Phishing URLs
- Malware-related links
- Suspicious domains
- Scam patterns
- Impersonation attempts

These findings could later be combined with graph analysis to investigate:

- Who created the content?
- How similar campaigns are connected.
- Whether suspicious content propagates through related addresses.
- Whether coordinated malicious behaviour exists.

---

# Current Limitation

The current dataset contains fields such as:

- Inscription ID
- Content
- Timestamp
- Block number
- Content length
- Value

However, it does **not** contain the richer transaction or address relationships required for graph-based blockchain analysis.

Additional blockchain data would therefore need to be collected (for example from a Bitcoin full node or a blockchain API).

---

# Possible Project Progression

## Current Project

**Content-Based Analysis of Bitcoin Inscriptions**

Current work includes:

- Parsing inscription datasets
- Rule-based content classification
- Monthly activity analysis
- Duplicate analysis
- Content frequency analysis
- Content length statistics
- Value statistics
- Storage analysis
- CSV export of analysis results

This serves as the preprocessing and exploratory analysis stage.

---

## Possible Bachelor Thesis

**Graph-Based Analysis of Bitcoin Inscriptions for Detecting Coordinated or Suspicious Activity**

Possible progression:

1. Parse and classify inscription content.
2. Perform exploratory statistical analysis.
3. Enrich the dataset with blockchain transaction and address information.
4. Construct inscription or transaction graphs.
5. Apply graph algorithms.
6. Detect coordinated or suspicious behaviour.
7. Evaluate the usefulness of graph-based techniques for blockchain forensics.

---

# Gap in Existing Literature

Most existing research focuses on questions such as:

- How do inscriptions affect Bitcoin?
- How much block space do they consume?
- How do they influence transaction fees?
- How much additional revenue do miners receive?

Relatively little work appears to focus on:

- What is actually being inscribed.
- How inscription content evolves over time.
- How different protocols emerge and change.
- Relationships between inscription contents.
- Graph-based analysis of inscription ecosystems.
- Cybersecurity-oriented analysis of inscription content.

This represents a potential opportunity for further research.

---

# Possible Thesis Titles

## Content Analysis

- Content-Based Analysis of Bitcoin Inscriptions: Evolution, Classification, and Storage Patterns
- Exploratory Analysis of Bitcoin Inscription Content and Protocol Evolution
- Large-Scale Content Analysis of Bitcoin Inscriptions
- Evolution of Bitcoin Inscription Content and Protocol Usage

## Graph Theory

- Graph-Based Analysis of Bitcoin Inscriptions for Detecting Coordinated Activity
- Graph-Based Detection of Coordinated Behaviour in Bitcoin Inscription Networks
- Network Analysis of Bitcoin Inscriptions Using Graph Theory
- Graph-Based Exploration of Bitcoin Inscription Ecosystems

## Cybersecurity

- Graph-Based Analysis of Bitcoin Inscriptions for Detecting Coordinated or Suspicious Activity
- Blockchain Forensics of Bitcoin Inscriptions Using Graph-Based Analysis
- Cybersecurity Analysis of Bitcoin Inscriptions Through Graph Mining
- Detecting Coordinated Behaviour in Bitcoin Inscriptions Using Graph Analytics