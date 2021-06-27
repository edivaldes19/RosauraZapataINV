package com.manuel.rosaurazapatainv.Firebase;

import androidx.annotation.NonNull;

public enum Colecciones {
    TECNICOPEDAGOGICO {
        @NonNull
        @Override
        public String toString() {
            return "Técnico Pedagógico";
        }
    },

    MATERIALDIDACTICO {
        @NonNull
        @Override
        public String toString() {
            return "Material Didáctico";
        }
    },

    MATERIALDECONSUMO {
        @NonNull
        @Override
        public String toString() {
            return "Material de Consumo";
        }
    },

    ACTIVOFIJO {
        @NonNull
        @Override
        public String toString() {
            return "Activo Fijo";
        }
    }
}