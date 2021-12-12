package wit.assignments.randomgeneratorapp.models

interface EntityBase {
    var id: Long
    var name: String
}

//EntityBase is a simple class that contains the data that is shared across all objects
//As data classes cannot be inherited I used an interface instead