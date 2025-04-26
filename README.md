# Bad Bunny BFS
NETS150 project - Deepika and Mehak

PROJECT TITLE: 
Bad Bunny Connects the World

DESCRIPTION: 
Our project uses BFS to find how Bad Bunny is connected to other artists, using a Spotify dataset (2019) pulled from Kaggle, of every song on the platform. Using the song titles and artist names, we made an adjacency list of how artists are connected via collaborations. The user inputs another artist, ie Taylor Swift or Lil Nas X, and the algorithm uses BFS to find the shortest path of songs linking Bad Bunny to one of these artists. Sample inputs and outputs- 
Ending Artist: Future
Output: No Me Conoce - Remix by Jhayco, J Balvin, Bad Bunny
Bum Bum Tam Tam by MC Fioti, Future, J Balvin, Stefflon Don, Juan Magán
We created an adjacency list of each Song and its Song nodes. Song is a class with Song Name (String) and Artists (List of Strings with artist names). Each element in the AL is linked to songs that it has at least one common artist with. After building our Song class and our adjacency list, we ran BFS, and printed out the 'path' from Bad Bunny to the ending artist. The songs on this path are part of the playlist, that is our final output. 

CATEGORIES: 
Graph building, Graph Algorithms, Social Networks 

WORK BREAKDOWN: 
Both-> deciding how to organize the AL and songs, deciding what dataset to use, brainstorming project ideas
Mehak-> Creating Song class, building Adjacency List after reading CSV file of Spotify dataset 
Deepika -> Creating BFSFinder method that runs BFS with specified end point, retrieving user input, printing out path
