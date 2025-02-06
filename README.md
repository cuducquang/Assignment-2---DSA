# Map2D Spatial Data Structure Project

This project encompasses the design and implementation of fundamental features
within a 2D map system, aligning its functionality with modern mapping services. It includes
the development of spatial indexing mechanisms, user interaction modules via a
command-line interface, and the establishment of data persistence mechanisms to store and
retrieve spatial data efficiently.

## The project covers these data structures and algorithms:

### Quad tree
The “QuadTree” class implements a spatial partitioning data structure known as a Quadtree.
This data structure is particularly useful for organizing and efficiently querying
two-dimensional spatial data.

Data Structure:
A tree data structure in which each internal node has exactly four children,
corresponding to four quadrants of the space being partitioned. Leaf nodes contain spatial
data.

Algorithms used:
1: Insertion:
● Description: Inserts a spatial element (Place) into the Quadtree  
● Algorithm: The insertion algorithm recursively traverses the Quadtree, dividing the
space into quadrants until reaching a leaf node. If the leaf node has available
capacity, the place is added to it. Otherwise, the leaf node is split, and places are
redistributed among the new child nodes.  

2: Splitting:
● Description: Splits a leaf node into four nodes when it reaches its capacity.  
● Algorithm: The splitting algorithm divides the space of a leaf node into four quadrants
and creates new child nodes for each quadrant. It then redistributed the places
contained in the original node among the newly created child nodes.  

3: Search by Rectangle:
● Description: Searches for places within a specified rectangular area.  
● Algorithm: The search algorithm recursively traverses the Quadtree, starting from the
root node. At each node, it checks if the node intersects with the specified
rectangular area. If it does, it searches for places within that node. If a leaf node is
reached, it checks each place within the node to determine if it falls within the
specified area and matches the desired service type.  

4: Removal:
● Description: Removes a place from the Quadtree.  
● Algorithm: The removal algorithm recursively traverses the Quadtree to locate the
node containing the specified place. If the place is found in a leaf node, it is removed
from the node’s list of places. If the removal causes a child node to become empty,
the child node is merged back into its parent.  

Inner Node class : “Node”  
● Description: Represents a node in the Quadtree.  
● Fields:  
        ○ ‘x’, ‘y’ : Coordinates of the node’s position  
        ○ ‘width’, ‘height’ : Dimensions of the node’s area  
        ○ ‘depth’ : Depth level of the node in the Quadtree  
        ○ ‘places’ : Set of places contained within the node (for leaf nodes).  
        ○ ‘children’ : Array of child nodes (for internal nodes)  
● Method:  
        ○ contains: checks if the node contains a specific place  
        ○ insert: inserts a place into the node  
        ○ isPartiallyContained: checks if the node is partially contained within a
        specified rectangle.  
        ○ intersect: checks if the node intersects with a specified rectangle  
        ○ reArrangePlaces: Rearranges the places within the node to optimize storage  

This Quadtree implementation provides efficient storage and retrieval of spatial data, making
it suitable for applications such as geographic information systems and spatial indexing.  
  
### Set
The “Set” class provides an implementation of a simple set data structure, which stores
unique elements without any particular order. It utilizes a ‘HashMap’ internally for efficient
element storage and retrieval.  
  
Data Structure:  
A collection that contains no duplicate elements  

Algorithms Used:  
1: Addition:  
● Description: Adds a new element to the set.  
● Algorithm: The addition algorithm inserts the element into the underlying HashMap,
with the element itself being the key and a null value.  
  
2: Contains:  
● Description: Checks if a given element is present in the set  
● Algorithm: The contains algorithm delegates the task to the underlying HashMap’s
‘containsKey’ method, which efficiently determines whether the specified element
exists in the set.  

3: Removal:  
● Description: Removes a specified element from the set  
● Algorithm: The removal algorithm removes the specified element from the underlying
in HashMap.  
  
4: Size:  
● Description: Returns the number of elements in the set  
● Algorithm: The size algorithm retrieves the size of the underlying HashMap, which
corresponding to the number of elements in the set  
  
5: Iteration  
● Description: Allows iteration over the elements of the set  
● Algorithm: The iteration algorithm provides an iterator that iterates over the
keys(elements) of the underlying HashMap, allowing sequential access to each
element in the set  
  
Inner iterator Class:  
● Description: Provides an iterator for traversing the elements of the set  
● Methods:  
        ○ hasNext : checks if there are more elements to iterate over  
        ○ next : Retrieves the next element in the iteration  
        ○ remove: Removes the last element returned by the iterator from the
underlying set  
This Set implementation offers efficient storage and retrieval of unique elements, making it
suitable for scenarios where uniqueness and simplicity are desired, such as maintaining a
collection of distinct items.  
  
### HashMap  
The ‘HashMap’ class implements a basic hash table data structure that stores key-value
pairs. It provides efficient insertion, retrieval, and removal operations based on the hash
code of the keys.  
  
Data Structure:  
A collection that stores key-value pairs, where each key is unique and maps to a
single value.  
  
Algorithms used:  
1: Initialization:  
● Description: Initializes the HashMap with a default capacity  
● Algorithm: creates an array of entries (Entry<K, V>[] table) with the default capacity
and initializes the size to zero  
  
2: Put:  
● Description: Associates the specified value with the specified key in the HashMap  
● Algorithm: Calculates the index for the key using the ‘getIndex’ method, then inserts
a new ‘Entry’ object with the key-value pair into the corresponding bucket. Handles
collisions by chaining entries in the same bucket.  
  
3: Get:  
● Description: Retrieves the value associated with the specified key from the HashMap  
● Algorithm: Calculates the index for the key using the ‘getIndex’ method, then
searches for the key in the corresponding bucket. Returns the associated value if
found, otherwise returns null.  
  
4: Contains Key:  
● Description: checks if the HashMap contains a mapping for the specified key  
● Algorithm: Utilizes the ‘has(K key)’ method to determine if the key exists in the
HashMap  
  
5: Size:  
● Description: Returns the number of key-value mappings in the HashMap  
● Algorithm: Returns the value of ‘size’ variable, which tracks the number of entries in
the HashMap  
  
6: Rehash:  
● Description: Rehashes the HashMap to increase capacity and redistribute the entries  
● Algorithm: creates a new HashMap(‘newMap’) and re-inserts all entries from the
current HashMap into the new one. Then replaces the current HashMap’s table and
size with the newMap’s table and size.  
  
7: GetOrDefault:  
● Description: Returns the value to which specified key is mapped, or a default value if
the key is not found  
● Algorithm: Utilizes the ‘get(K key)’ method to retrieve the value for the key. If the key
is not found, returns the specified default value  
  
8: Removal:  
● Description: Removes the mapping for a key from the HashMap if it is present.  
● Algorithm: Calculates the index for the key using ‘getIndex’ method, then traverses
the chain of entries in the corresponding bucket to find and remove the entry with the
specified key  
  
9: Clear:  
● Description: Removes all key-value mappings from the HashMap  
● Algorithm: Sets all entries in the HashMap’s table to null and resets the size to zero  
  
10: Entry Set:  
● Description: Returns a set view of the key-value mappings contained in the HashMap  
● Algorithm: Constructs a Set of ‘Entry<K, V>’ objects by iterating through each bucket
in the HashMap’s table and adding all non-null entries to the set  

11: Values:  
● Description: Returns a collection view of the values contained in the HashMap  
● Algorithm: Constructs a Set of values by iterating through each bucket in the
HashMap’s table and adding all non-null values to the set.  
  
12: Iteration:  
● Description: Allows iteration over the key-value mappings in the HashMap  
● Algorithm: Provides an iterator that traverses the HashMap’s table and its entry
chains, allowing sequential access to each key-value pair  
  
Inner Entry Class:  
● Description: Represents a key-value pair stored in the HashMap  
● Fields:  
        ○ ‘key’ : The key of the entry  
        ○ ‘value’ : The value associated with the key  
        ○ ‘next’ : Reference to the next entry in the chain (for handling collisions)  
  
### List
The ‘List’ class represents a dynamic array that can dynamically resize itself as elements are
added. It provides functionality to add elements, retrieve elements by index, and iterate over
its elements  
  
Data Structure:  
A dynamic array that grows or shrinks in size elements are added or removed  
Algorithms used:  
  
1: Initialization:  
● Description: Initializes the List with a default capacity  
● Algorithm: Creates an array (‘Object[] elements’) with the default capacity and sets
the initial size to zero  
  
2: Add:  
● Description: Adds an element to the end of the List  
● Algorithm: Checks if the List is at full capacity, and if so, increases its capacity by
doubling the size of the underlying array. Then adds the element to the end of the
array and increments the size  
  
3: Get:  
● Description: Retrieves the element at the specified index from the List  
● Algorithm: Checks if the index is within the valid range (0 to size -1), then retrieves
and returns the element at that index from the array  
  
4: Size:  
● Description: Returns the number of elements in the List  
● Algorithm: Returns the value of the ‘size’ variable, which tracks the number of
elements in the List  
  
5: Ensure Capacity:  
● Description: Ensures that the underlying array has enough capacity to accommodate
additional elements  
● Algorithm: If the current size of the array is equal to its length, doubles the size of the
array using ‘Arrays.copyOf()’  
  
6: Iteration:  
● Description: Allows iteration over the elements in the List  
● Algorithm: Provides an iterator that traverses the elements in the List, allowing
sequential access to each element  
Inner Iterator Class:  
● Description: Provides an iterator for iterating over the elements in the List  
● Fields:  
        ○ ‘index’ : The current index being iterated over  
● Methods:  
        ○ ‘hasNext’ : Returns true if there are more elements to iterate over  
        ○ ‘next’ : returns the next element in the List and advances the iterator position  

### Arrays
The ‘Arrays’ class provides utility methods for working with arrays, including creating Lists
from arrays and copying arrays  

Algorithm used:  
1: asList:  
● Description: Creates a List from an array  
● Algorithm:  
        ○ creates a new instance of List  
        ○ iterates over the input elements array and adds each element to the List   
        ○ returns the List containing the elements  
  
2: copyOf:  
● Description: Copies the specified array, truncating or padding with nulls (if necessary)
so the copy has the specified length  
● Algorithm:  
        ○ creates a new array (‘copy’) with the specified length (‘newLength’)  
        ○ copies elements from the original array to the new array using
        ‘System.arraycopy()’  
        ○ If the origin array length is less than ‘newLength’, the remaining elements in
        the new array are set to null  
        ○ Returns the copied array  
          
### Place BinarySearch
The ‘Place BinarySearch’ class provides binary search algorithms to find the start and end
indices of places in a sorted list based on their x-coordinate.  

Algorithms used:  
1: Binary search for Start:
● Description: Searches for the index of the first place with an x-coordinate equal to or
greater than the target  
● Algorithm: The binary search for start algorithm divides the sorted list into halves,
iteratively narrowing down the search range until the target x-coordinate is found or
the search range is exhausted. If the exact target is found, its index is returned.
Otherwise, the index of the first place with an x-coordinate greater than the target is
returned  
  
2: Binary search for End:  
● Description: Searches for the index of the last place with an x-coordinate equal to or
less than the target  
● Algorithm: The binary search for end algorithm also divides the sorted list into halves,
iteratively narrowing down the search range. If the exact target is found, its index is
returned. Otherwise, the index of the last place with an x-coordinate less than the
target is returned  

### Place QuickSort
The ‘Place QuickSort’ class implements the QuickSort algorithm to efficiently sort a list of
places.  

Algorithms used:  
1: QuickSort:  
● Description: Sorts a list of places in ascending order  
● Algorithm: The QuickSort algorithm recursively divides the list into smaller sublists
based on a pivot element. Elements smaller than the pivot are placed to the left, and
elements greater than the are placed to the right. This process continues until the
entire list is sorted  
  
2: Partitioning:  
● Description: Partitions the list into two parts, with elements smaller than the pivot on
the left and elements greater than the pivot on the right.  
● Algorithm: The partitioning algorithm selects a pivot element and rearranges the list
such that all elements smaller than the pivot are placed to its left, and all elements
greater than the pivot are placed to its right. The pivot is then moved to its correct
sorted position  
  
3: Swap:  
● Description: Swaps two elements in a list  
● Algorithm: The swap algorithm exchanges the positions of two elements in the list  





