graph [
  DateObtained "3/02/11"
  GeoLocation "US"
  GeoExtent "Country"
  Network "Abilene"
  Provenance "Primary"
  Access 0
  Source "http://www.internet2.edu/pubs/200502-IS-AN.pdf"
  Version "1.0"
  Type "REN"
  DateType "Historic"
  Backbone 1
  Commercial 0
  label "Abilene"
  ToolsetVersion "0.3.34dev-20120328"
  Customer 0
  IX 0
  SourceGitVersion "e278b1b"
  DateModifier "="
  DateMonth "02"
  LastAccess "3/02/11"
  Layer "IP"
  Creator "Topology Zoo Toolset"
  Developed 0
  Transit 0
  NetworkDate "2005_02"
  DateYear "2005"
  LastProcessed "2011_09_01"
  Testbed 0
	node [
	  id 12
	  label "12"
	  Country "13"
	  Longitude 40.0
	  Internal 1
	  Latitude 39.0
	]
	node [
	  id 13
	  label "13"
	  Country "13"
	  Longitude 20.0
	  Internal 1
	  Latitude 28.0
	]
	node [
	  id 14
	  label "14"
	  Country "13"
	  Longitude 10.0
	  Internal 1
	  Latitude 14.0
	]
	edge [
	  source 12
	  target 1
	  LinkType "OC-192"
	  LinkLabel "OC-192c"
	  LinkNote "c"
	]
	edge [
	  source 12
	  target 2
	  LinkType "OC-192"
	  LinkLabel "OC-192c"
	  LinkNote "c"
	]
	edge [
	  source 12
	  target 13
	  LinkType "OC-192"
	  LinkLabel "OC-192c"
	  LinkNote "c"
	]
	edge [
	  source 13
	  target 3
	  LinkType "OC-192"
	  LinkLabel "OC-192c"
	  LinkNote "c"
	]
	edge [
	  source 13
	  target 12
	  LinkType "OC-192"
	  LinkLabel "OC-192c"
	  LinkNote "c"
	]
	edge [
	  source 13
	  target 14
	  LinkType "OC-192"
	  LinkLabel "OC-192c"
	  LinkNote "c"
	]
	edge [
	  source 14
	  target 3
	  LinkType "OC-192"
	  LinkLabel "OC-192c"
	  LinkNote "c"
	]
	edge [
	  source 14
	  target 7
	  LinkType "OC-192"
	  LinkLabel "OC-192c"
	  LinkNote "c"
	]
	edge [
	  source 14
	  target 13
	  LinkType "OC-192"
	  LinkLabel "OC-192c"
	  LinkNote "c"
	]
]
