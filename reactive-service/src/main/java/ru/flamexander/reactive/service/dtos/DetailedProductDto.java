package ru.flamexander.reactive.service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetailedProductDto  {
    private Long id;
    private String name;
    private String description;
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        } else {
            var other = (DetailedProductDto)obj;
            var eqName = other.getName()==null && this.getName()==null ||
                    other.getName()!=null && other.getName().equals(this.getName());
            var eqDescription =other.getDescription() == null && this.getDescription() ==null ||
                    other.getDescription()!=null && other.getDescription().equals(this.getDescription());
            return other.getId().equals(this.getId()) &&
                    eqName &&
                    eqDescription;
        }
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}
