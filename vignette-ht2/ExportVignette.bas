Attribute VB_Name = "ExportVignette"
Option Explicit

Public Sub AjouterVehicule()
    Dim ws As Worksheet
    Dim libelles As Variant
    Dim valeurs(1 To 6) As String
    Dim reponse As Variant
    Dim i As Long
    Dim prochaineLigne As Long

    libelles = Array( _
        "Part_1_IMMATRICUL", _
        "Part_2_IMMATRICUL", _
        "Part_3_IMMATRICUL", _
        "PUISSANCE_FISCALE", _
        "TYPE_CARBURANT", _
        "ANNEE")

    For i = 1 To 6
        reponse = Application.InputBox( _
            Prompt:="Saisissez " & libelles(i - 1) & " :", _
            Title:="Ajout d'un véhicule (" & i & "/6)", _
            Type:=2)

        If VarType(reponse) = vbBoolean Then Exit Sub
        valeurs(i) = Trim$(CStr(reponse))

        If Len(valeurs(i)) = 0 Then
            MsgBox "Tous les champs sont obligatoires. Le véhicule n'a pas été ajouté.", vbExclamation
            Exit Sub
        End If
    Next i

    Set ws = ThisWorkbook.Worksheets("Données")
    prochaineLigne = ws.Cells(ws.Rows.Count, 1).End(xlUp).Row + 1
    If prochaineLigne < 2 Then prochaineLigne = 2

    For i = 1 To 6
        ws.Cells(prochaineLigne, i).NumberFormat = "@"
        ws.Cells(prochaineLigne, i).Value = valeurs(i)
    Next i

    MsgBox "Véhicule ajouté à la ligne " & prochaineLigne & ".", vbInformation
End Sub

Public Sub GenererCSV()
    Dim ws As Worksheet
    Dim derniereLigne As Long
    Dim ligne As Long
    Dim colonne As Long
    Dim champs(1 To 6) As String
    Dim chemin As Variant
    Dim numeroFichier As Integer
    Dim fichierOuvert As Boolean

    Set ws = ThisWorkbook.Worksheets("Données")
    derniereLigne = ws.Cells(ws.Rows.Count, 1).End(xlUp).Row

    If derniereLigne < 2 Then
        MsgBox "Ajoutez au moins un véhicule avant de générer le CSV.", vbExclamation
        Exit Sub
    End If

    For ligne = 2 To derniereLigne
        For colonne = 1 To 6
            If Len(Trim$(CStr(ws.Cells(ligne, colonne).Value))) = 0 Then
                MsgBox "Champ manquant à la ligne " & ligne & ", colonne " & colonne & ".", vbExclamation
                Exit Sub
            End If
        Next colonne
    Next ligne

    chemin = Application.GetSaveAsFilename( _
        InitialFileName:="vignette-HT2.csv", _
        FileFilter:="Fichier CSV (*.csv), *.csv")

    If VarType(chemin) = vbBoolean Then Exit Sub
    If Len(Dir$(CStr(chemin))) > 0 Then
        If MsgBox("Le fichier existe déjà. Voulez-vous le remplacer ?", _
            vbQuestion Or vbYesNo) = vbNo Then Exit Sub
    End If

    On Error GoTo ErreurEnregistrement
    numeroFichier = FreeFile
    Open CStr(chemin) For Output As #numeroFichier
    fichierOuvert = True

    Print #numeroFichier, "Part_1_IMMATRICUL,Part_2_IMMATRICUL,Part_3_IMMATRICUL,PUISSANCE_FISCALE,TYPE_CARBURANT,ANNEE"

    For ligne = 2 To derniereLigne
        For colonne = 1 To 6
            champs(colonne) = CStr(ws.Cells(ligne, colonne).Value)
            champs(colonne) = ChampCSV(champs(colonne))
        Next colonne
        Print #numeroFichier, Join(champs, ",")
    Next ligne

    Close #numeroFichier
    fichierOuvert = False
    MsgBox "Le fichier CSV a été généré :" & vbCrLf & CStr(chemin), vbInformation
    Exit Sub

ErreurEnregistrement:
    If fichierOuvert Then Close #numeroFichier
    MsgBox "Impossible de générer le fichier CSV : " & Err.Description, vbCritical
End Sub

Private Function ChampCSV(ByVal valeur As String) As String
    If InStr(1, valeur, """") > 0 Then
        valeur = Replace(valeur, """", """""")
    End If
    If InStr(1, valeur, ",") > 0 Or InStr(1, valeur, """") > 0 _
        Or InStr(1, valeur, vbCr) > 0 Or InStr(1, valeur, vbLf) > 0 Then
        valeur = """" & valeur & """"
    End If
    ChampCSV = valeur
End Function
