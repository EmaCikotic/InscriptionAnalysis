# TODO

## Data Processing
- [x] Set up the Java project
- [x] Read inscription data from the dataset
- [x] Parse JSON records into Inscription objects
- [x] Process the complete dataset
- [x] Count total inscriptions

## Dataset Exploration
- [x] Count total inscriptions
- [x] Determine the dataset date range
- [x] Analyze monthly inscription activity
- [ ] Count unique inscription contents
- [ ] Identify duplicate/repeated content
- [ ] Find the most frequently repeated content
- [ ] Identify periods with unusually high activity
- [ ] Analyze content length distribution
- [ ] Investigate the meaning and distribution of the `value` field

## Data Collection — Bachelor Thesis Extension
> Required if this project is developed into a bachelor thesis.

- [ ] Set up and synchronize a Bitcoin full node
- [ ] Investigate how inscriptions can be extracted from Bitcoin blockchain data
- [ ] Determine the exact time period missing from the current dataset
- [ ] Extract inscriptions for the missing period
- [ ] Convert extracted inscriptions to the existing dataset format
- [ ] Merge the new data with the existing dataset
- [ ] Validate the completeness and consistency of the combined dataset
- 
## Content Analysis
- [ ] Identify common content formats and structures
- [ ] Detect structured content (e.g. JSON)
- [ ] Extract URLs and domains from inscription content
- [ ] Analyze the most frequently occurring domains
- [ ] Explore methods for measuring content similarity
- [ ] Group similar inscriptions into content clusters

## Potential Security Analysis
- [ ] Investigate repeated or coordinated content patterns
- [ ] Explore detection of potentially automated inscription activity
- [ ] Compare extracted domains/URLs with security data sources
- [ ] Investigate transaction data available through inscription IDs
- [ ] Explore construction of a transaction/address graph
- [ ] Evaluate graph-based methods for detecting coordinated activity

## Research
- [ ] Review existing research on blockchain inscriptions
- [ ] Identify limitations of existing approaches
- [ ] Define a potential research question
- [ ] Decide whether the project will be developed into a bachelor thesis