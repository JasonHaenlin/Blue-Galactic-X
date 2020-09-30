package fr.unice.polytech.soa.team.j.bluegalacticx.client.api.rocket.models;

public class RocketStatus {
    String status;

    public String getStatus(){
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "{ 'status' : '" + status + "' }";
    }

    @Override
    public boolean equals(Object obj) {
        // If the object is compared with itself then return true   
        if (obj == this) { 
            return true; 
        } 
    
        /* Check if obj is an instance of WeatherStatus or not 
            "null instanceof [type]" also returns false */
        if (!(obj instanceof RocketStatus)) { 
            return false; 
        } 
            
        // typecast o to Complex so that we can compare data members  
        RocketStatus s = (RocketStatus) obj; 
            
        // Compare the data members and return accordingly  
        return s.status.equals(this.status);
    }
}
