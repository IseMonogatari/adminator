package com.example.Preproject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DuckDTO {
    private Integer id;
    private String url;
    private String message;

    public DuckDTO(Integer id, String url) {
        this.id = id;
        this.url = url;
    }

    @Override
    public String toString() {
        return "\nDuckDTO{" +
                "id=" + id +
                ", url='" + url + '\'' +
                '}' + "\n";
    }
}
