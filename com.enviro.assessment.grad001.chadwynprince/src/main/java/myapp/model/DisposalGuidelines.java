package myapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

@Entity
public class DisposalGuidelines {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String disposalGuideline;

    public DisposalGuidelines(long id, String disposalGuideline){
        this.id = id;
        this.disposalGuideline = disposalGuideline;
    }

    public DisposalGuidelines(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDisposalGuideline() {
        return disposalGuideline;
    }

    public void setDisposalGuideline(String disposalGuideline) {
        this.disposalGuideline = disposalGuideline;
    }
}
