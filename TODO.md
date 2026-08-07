# TODO

## Data Processing
- [x] Set up the Java project
- [x] Read inscription data from the dataset
- [x] Parse JSON records into `Inscription` objects
- [x] Process the complete dataset
- [x] Count total inscriptions
- [x] Refactor the analysis into separate classes

## Dataset Exploration
- [x] Determine the dataset date range
- [x] Analyze monthly inscription activity
- [x] Count unique inscription contents per month
- [x] Calculate duplicate inscription counts per month
- [x] Identify periods with unusually high activity
- [x] Count global content frequencies
- [x] Find the most frequently repeated content
- [x] Export content-frequency results to CSV
- [x] Add duplicate percentages to monthly statistics
- [x] Analyze content length statistics
- [x] Analyze content length distribution
- [x] Analyze value statistics
- [x] Analyze average content length per content type
- [ ] Investigate the meaning and distribution of the `value` field

## Content Analysis
- [x] Implement rule-based content classification
- [x] Detect empty content
- [x] Detect JSON content
- [x] Detect HTML content
- [x] Detect direct URLs
- [x] Detect inscription references
- [x] Detect Bitmap identifiers
- [x] Detect namespace identifiers
- [x] Detect single-character content
- [x] Export unclassified content for inspection
- [ ] Inspect and classify remaining `Other` content
- [ ] Identify additional common formats and protocol structures
- [ ] Extract domains from inscription content
- [ ] Analyze the most frequently occurring domains
- [ ] Explore methods for measuring content similarity
- [ ] Group similar inscriptions into content clusters
  
## Exported Results
- [x] Export content type statistics to CSV
- [x] Export monthly statistics to CSV
- [x] Export content types to CSV
- [x] Export average content length per type to CSV
- [x] Export unclassified contents to CSV

## Data Collection — Bachelor Thesis Extension
> Required if this project is developed into a bachelor thesis.

- [ ] Set up and synchronize a Bitcoin full node
- [ ] Investigate how inscriptions can be extracted from Bitcoin blockchain data
- [ ] Determine the exact time period missing from the current dataset
- [ ] Extract inscriptions for the missing period
- [ ] Convert extracted inscriptions to the existing dataset format
- [ ] Merge the new data with the existing dataset
- [ ] Validate the completeness and consistency of the combined dataset

## Potential Security Analysis
- [ ] Investigate repeated or coordinated content patterns
- [ ] Explore detection of potentially automated inscription activity
- [ ] Compare extracted domains and URLs with security data sources
- [ ] Investigate transaction data available through inscription IDs
- [ ] Explore construction of an inscription-reference graph
- [ ] Explore construction of a transaction or address graph
- [ ] Evaluate graph-based methods for detecting coordinated activity

## Research
- [ ] Review existing research on Bitcoin inscriptions
- [ ] Compare implemented analyses with existing approaches
- [ ] Identify limitations of existing work
- [ ] Define a potential research question
- [ ] Decide whether the project will be developed into a bachelor thesis
