/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Enums;

/**
 *
 * @author Elias
 */
public enum SurfaceGenerationEnum {
    Rotation(0), Translation(1);

    public int generationType;

    SurfaceGenerationEnum(int value) {
        generationType = value;
    }

    public int getValue() {
        return generationType;
    }
}
