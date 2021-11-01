/* v4_kid */
INSERT INTO symptoms (name, version, text_de, text_fr, text_it, text_en)
VALUES ('NO_SYMPTOMS', 'v4_kid', 'Kein Symptom (aus der Liste unten)', 'Aucun symptôme (de la liste ci-dessous)', 'Nessun sintomo (di quelli sottoelencati)', 'No symptom (from the list below)'),
       ('FEVER', 'v4_kid', 'Fieber', 'Fièvre', 'Febbre', 'High temperature'),
       ('WEAKNESS', 'v4_kid', 'Allgemeine Schwäche, Unwohlsein', 'Faiblesse générale, sensation de malaise', 'Malessere, debolezza generale', 'General weakness, feeling unwell'),
       ('HEADACHES', 'v4_kid', 'Kopfschmerzen', 'Maux de tête', 'Mal di testa', 'Headache'),
       ('SNIFFLES', 'v4_kid', 'Schnupfen', 'Rhume', 'Raffreddore', 'Head cold'),
       ('SORE_THROAT', 'v4_kid', 'Halsschmerzen', 'Maux de gorge', 'Mal di gola', 'Sore throat'),
       ('COUGH', 'v4_kid', 'Husten', 'Toux', 'Tosse', 'Cough'),
       ('SHORTNESS_BREATH', 'v4_kid', 'Kurzatmigkeit', 'Insuffisance respiratoire', 'Affanno', 'Shortness of breath'),
       ('CHEST_PAIN', 'v4_kid', 'Brustschmerzen', 'Douleurs dans la poitrine', 'Dolori al petto', 'Chest pain'),
       ('GASTRO', 'v4_kid', 'Magen-Darm-Symptome (Übelkeit, Erbrechen, Durchfall, Bauchschmerzen)', 'Symptômes gastro-intestinaux (nausées, vomissements, diarrhée, maux de ventre)', 'Sintomi gastrointestinali (nausea, vomito, diarrea, dolori addominali)', 'Gastrointestinal symptoms (nausea, vomiting, diarrhoea, stomach ache)'),
       ('MUSCLE_PAIN', 'v4_kid', 'Muskelschmerzen', 'Douleurs musculaires', 'Dolori muscolari', 'Aching muscles'),
       ('LOST_SMELL_OR_TASTE', 'v4_kid', 'Plötzlicher Verlust des Geruchs- und/oder Geschmackssinns', 'Perte soudaine de l’odorat et/ou du goût', 'Perdita improvvisa dell’olfatto e/o del gusto', 'Sudden loss of sense of smell and/or taste'),
       ('SKIN_RASH', 'v4_kid', 'Hautausschlag', 'Éruptions cutanées', 'Eruzioni cutanee', 'Skin rash');

INSERT INTO answers (name, version, text_de, text_fr, text_it, text_en)
VALUES ('ANSWER_NO', 'v4_kid', 'Nein', 'Non', 'No', 'No'),
       ('ANSWER_YES', 'v4_kid', 'Ja', 'Oui', 'Si', 'Yes'),
       ('ANSWER_IDK', 'v4_kid', 'Ich weiss es nicht', 'Je ne sais pas', 'Non lo so', 'I don''t know');

INSERT INTO recommendations (name, version, severity,
                     title_de, title_fr, title_it, title_en,
                     description_de, description_fr, description_it, description_en)
VALUES
        ('RECOMMENDATION_KBAMBINI', 'v4_kid', 'HIGH',
        '<div style=\"color: black\"><p><strong><span style=\"font-size: \n      18px;\">Resultat:</span></strong></p>\n<p>Da Sie ein Alter unter 12 Jahren eingegeben haben, empfiehlt Ihnen das BAG, das Onlinetool <a href=\"www.coronabambini.ch\" rel=\"noopener noreferrer\" target=\"_blank\">www.coronabambini.ch</a> zu verwenden. Es wurde von den Kinderkliniken und Notfallzentren des Inselspitals in Bern entwickelt.  \n</p></div>',
        '<div style=\"color: black\"><p><strong><span style=\"font-size: \n      18px;\">Résultat:</span></strong></p>\n<p>Comme vous avez indiqué un âge inférieur à 12 ans, l''OFSP vous recommande d''utiliser l''outil en ligne <a href=\"www.coronabambini.ch\" rel=\"noopener noreferrer\" target=\"_blank\">www.coronabambini.ch</a>. Il a été développé par les cliniques pédiatriques et les centres d''urgence de l''Inselspital de Berne.\n</p></div>',
        '<div style=\"color: black\"><p><strong><span style=\"font-size: \n      18px;\">Risultato:</span></strong></p>\n<p>Poiché ha immesso un’età inferiore ai 12 anni, l’UFSP Le raccomanda di utilizzare lo strumento online <a href=\"www.coronabambini.ch\" rel=\"noopener noreferrer\" target=\"_blank\">www.coronabambini.ch</a>, sviluppato dalle cliniche pediatriche e dai centri di emergenza dell’Inselspital di Berna.  \n</p></div>',
        '<div style=\"color: black\"><p><strong><span style=\"font-size: \n      18px;\">Result:</span></strong></p>\n<p>As you said you are under 12, the FOPH recommends you use the online tool <a href=\"www.coronabambini.ch\" rel=\"noopener noreferrer\" target=\"_blank\">www.coronabambini.ch</a>. It was developed by the children’s hospitals and the emergency centres at the Inselspital in Bern.  \n</p></div>',
        '<div style=\"color: black\"><p><strong><span style=\"font-size: \n      18px;\">Empfehlung:</span></strong></p>\n<p>Bitte besuchen Sie <a href=\"www.coronabambini.ch\" rel=\"noopener noreferrer\" target=\"_blank\">www.coronabambini.ch</a> (externe Website).  \n</p></div>',
        '<div style=\"color: black\"><p><strong><span style=\"font-size: \n      18px;\">Recommendation:</span></strong></p>\n<p>Veuillez consulter le site <a href=\"www.coronabambini.ch\" rel=\"noopener noreferrer\" target=\"_blank\">www.coronabambini.ch</a> (site Internet externe).\n</p></div>',
        '<div style=\"color: black\"><p><strong><span style=\"font-size: \n      18px;\">Raccomandazione:</span></strong></p>\n<p>Visiti il sito <a href=\"www.coronabambini.ch\" rel=\"noopener noreferrer\" target=\"_blank\">www.coronabambini.ch</a> (sito web esterno).\n</p></div>',
        '<div style=\"color: black\"><p><strong><span style=\"font-size: \n      18px;\">Recommendation:</span></strong></p>\n<p>Please visit <a href=\"www.coronabambini.ch\" rel=\"noopener noreferrer\" target=\"_blank\">www.coronabambini.ch</a> (external website).\n</p></div>');