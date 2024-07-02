#!/usr/bin/python3
# -*- coding: utf-8 -*-
#from Factores import *
from Factor import *

MA = Variable("MA",['0','1'])
MP = Variable("MP",['0','1'])
AA = Variable("AA",['0','1'])
AP = Variable("AP",['0','1'])
JA = Variable("JA",['0','1'])
JP = Variable("JP",['0','1'])
BA = Variable("BA",['0','1'])

BP = Variable("BP",['0','1'])
LI = Variable("LI",['0','1'])
LF = Variable("LF",['0','1'])
FIN = Variable("FIN",['0','1'])
EE = Variable("EE",['0','1'])
IT = Variable("IT",['0','1'])
VP = Variable("VP",['0','1'])

## Marginales
pLF = Factor((LF,),[0.78,0.22], tabla_de_valores([LF]))
pLI = Factor((LI,),[0.78,0.22], tabla_de_valores([LI]))
# Declara las demás distribuciones...
pFIN = Factor((FIN,),[0.72,0.28], tabla_de_valores([FIN]))
pIT = Factor((IT,),[0.7,0.3], tabla_de_valores([IT]))
pEE = Factor((EE,),[0.35,0.65], tabla_de_valores([EE]))

## Condicionales
pJA_LI = Factor((JA,LI),[0.1, 0.6, 0.9, 0.4], tabla_de_valores([JA,LI]))
pAA_FIN = Factor((AA,FIN),[0.6,0.2,0.4,0.8], tabla_de_valores([AA,FIN]))
# Declara las demás distribuciones...
pBA_IT = Factor((BA,IT),[0.05,0.7,0.95,0.3], tabla_de_valores([BA,IT]))
pMP_MA = Factor((MP,MA),[0.97,0.03,0.03,0.97], tabla_de_valores([MP,MA]))
pAP_AA = Factor((AP,AA),[0.5,0.2,0.5,0.8], tabla_de_valores([AP,AA]))

pMA_JAAA = Factor((MA,JA,AA),[0.5,0.15,0.05,0.95,0.5,0.85,0.95,0.05], tabla_de_valores([MA,JA,AA]))
pVP_AABA = Factor((VP,AA,BA),[0.3,0.6,0.1,0,0.7,0.4,0.9,1],tabla_de_valores([VP,AA,BA]))
# Declara las demás distribuciones...
pJP_JALF = Factor ((JP,JA,LF),[0.6,1,0.1,0.3,0.4,0,0.9,0.7], tabla_de_valores([JP,JA,LF]))
pBP_BAEE = Factor ((BP,BA,EE),[0.8,1,0.05,1,0.2,0,0.95,0], tabla_de_valores([BP,BA,EE]))


def pLuviaInv_NoMayNoFin():
    print("P(li|¬mp,¬fin)")
    Prob = pMP_MA.reduccion('MP','0').multiplicacion(pMA_JAAA).marginalizacion('MA')
    print(Prob)
    Prob = Prob.multiplicacion(pAA_FIN.reduccion('FIN','0')).marginalizacion('AA')
    print(Prob)
    Prob = Prob.multiplicacion(pJA_LI).marginalizacion('JA').multiplicacion(pFIN.reduccion('FIN','0')).multiplicacion(pLI)
    print(Prob)
    Prob = Prob.normalizacion()
    print(Prob)
    Prob = Prob.reduccion('LI', '1')
    print("Resultado FINAL = \n" , Prob)
    

if __name__ == '__main__':
    pLuviaInv_NoMayNoFin()


