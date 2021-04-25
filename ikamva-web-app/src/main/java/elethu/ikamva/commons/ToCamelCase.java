package elethu.ikamva.commons;

import org.springframework.stereotype.Component;

@Component
public class ToCamelCase {

    public String ConvertToCamelCase(String s){
        return s.substring(0, 1).toUpperCase() +
                s.substring(1);
    }
}
