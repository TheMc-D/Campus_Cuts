-- Starter reference list of schools for development/demo purposes only.
--
-- This is NOT the full list of ~6,000 US degree-granting (Title IV-eligible) institutions.
-- The authoritative source for the full list is the U.S. Department of Education's IPEDS
-- directory data (https://nces.ed.gov/ipeds/use-the-data), or the derived College Scorecard
-- dataset (https://collegescorecard.ed.gov/data/). Loading the real list is a separate,
-- later data task: download/transform an IPEDS or College Scorecard extract offline
-- (name + state), then load it as its own later Flyway migration (e.g. V3) generated from
-- that extract — not something to hand-write here.

INSERT INTO schools (name, state) VALUES
    ('University of Alabama', 'AL'),
    ('Arizona State University', 'AZ'),
    ('University of Arkansas', 'AR'),
    ('University of California, Berkeley', 'CA'),
    ('University of California, Los Angeles', 'CA'),
    ('Stanford University', 'CA'),
    ('University of Colorado Boulder', 'CO'),
    ('University of Connecticut', 'CT'),
    ('University of Florida', 'FL'),
    ('Florida State University', 'FL'),
    ('University of Georgia', 'GA'),
    ('Georgia Institute of Technology', 'GA'),
    ('University of Illinois Urbana-Champaign', 'IL'),
    ('Indiana University Bloomington', 'IN'),
    ('University of Iowa', 'IA'),
    ('University of Kansas', 'KS'),
    ('Louisiana State University', 'LA'),
    ('University of Maryland, College Park', 'MD'),
    ('University of Michigan', 'MI'),
    ('Michigan State University', 'MI'),
    ('University of Minnesota Twin Cities', 'MN'),
    ('University of Missouri', 'MO'),
    ('University of Nebraska-Lincoln', 'NE'),
    ('Rutgers University-New Brunswick', 'NJ'),
    ('New York University', 'NY'),
    ('Columbia University', 'NY'),
    ('University of North Carolina at Chapel Hill', 'NC'),
    ('North Carolina State University', 'NC'),
    ('Ohio State University', 'OH'),
    ('University of Oklahoma', 'OK'),
    ('Pennsylvania State University', 'PA'),
    ('University of South Carolina', 'SC'),
    ('University of Tennessee, Knoxville', 'TN'),
    ('University of Texas at Austin', 'TX'),
    ('Texas A&M University', 'TX'),
    ('University of Virginia', 'VA'),
    ('Virginia Tech', 'VA'),
    ('University of Washington', 'WA'),
    ('University of Wisconsin-Madison', 'WI');
