INSERT INTO answers (name, version, text_de, text_fr, text_it, text_en)
VALUES ('ANSWER_NO', 'v3_kid', 'Nein', 'Non', 'No', 'No'),
       ('ANSWER_YES', 'v3_kid', 'Ja', 'Oui', 'Si', 'Yes'),
       ('ANSWER_IDK', 'v3_kid', 'Ich weiss es nicht', 'Je ne sais pas', 'Non lo so', 'I don''t know');

INSERT INTO questions (name, version,
                       title_de, title_fr, title_it, title_en,
                       description_de, description_fr, description_it, description_en)
VALUES

       ('QUESTION_COUGH', 'v3_kid',
        '<p>Haben Sie eines oder mehrere Symptome einer akuten Atemwegserkrankung?</p>',
        '<p>Avez-vous un ou plusieurs symptômes d''une maladie respiratoire aiguë ?</p>',
        '<p>Ha uno o più sintomi di una malattia respiratoria acuta?</p>',
        '<p>Do you have one or more symptoms of acute respiratory disease?</p>',
        '<p>z. B. Husten, Halsschmerzen oder Kurzatmigkeit.</p>',
        '<p>Par exemple de la toux, un mal de gorge ou de l''essoufflement.</p>',
        '<p>Per esempio tosse, mal di gola o affanno.</p>',
        '<p>e.g. cough, sore throat or shortness of breath.</p>'),

       ('QUESTION_FEVER_COUGH_NO', 'v3_kid',
        '<p>Haben Sie aktuell Fieber, ein Fiebergefühl oder Muskelschmerzen? Oder hatten Sie in den letzten 24 Stunden eines oder mehrere dieser Symptome?</p>',
        '<p>Avez-vous de la fièvre, une sensation de fièvre ou des douleurs musculaires ? Ou avez-vous eu un ou plusieurs de ces symptômes dans les dernières 24 heures ?</p>',
        '<p>Al momento ha la febbre, sensazione di febbre o dolori muscolari? Oppure ha avuto uno o più di questi sintomi nelle ultime 24 ore?</p>',
        '<p>Do you currently have a fever, a feverish feeling or muscle pain? \nOr have you had one or more of these symptoms in the last 24 hours?</p>',
        '', '', '', ''),

       ('QUESTION_CONTACT', 'v3_kid',
        '<p>Hatten Sie engen Kontakt zu einer Person mit einer akuten \nAtemwegserkrankung (Husten, Halsschmerzen, Kurzatmigkeit \nmit oder ohne Fieber, Fiebergefühl, Muskelschmerzen)?</p>',
        '<p>Avez-vous été en contact étroit avec une personne atteinte d’une maladie respiratoire aiguë (toux, maux de gorge, essoufflement avec ou sans fièvre, sensation de fièvre, douleurs musculaires) ?</p>',
        '<p>Ha avuto un contatto stretto con una persona affetta da una malattia respiratoria acuta (tosse, mal di gola, affanno con o senza febbre, sensazione di febbre, dolori muscolari)?</p>',
        '<p>Have you been in close contact with a person with acute respiratory disease (cough, sore throat, shortness of breath with or without fever, feverish feeling, muscle pain)?</p>',
        '<p>Ein enger Kontakt ist gegeben, wenn Sie im selben Haushalt leben oder eine intime Beziehung mit dieser Person haben. Der Kontakt muss stattgefunden haben, während die erkrankte Person ansteckend war (d.h. als sie Symptome hatte und/oder in den 24 Stunden vor dem Auftreten der Symptome).</p>',
        '<p>Un contact étroit est donné si vous vivez dans le même ménage ou si vous avez une relation intime avec cette personne. Le contact doit avoir eu lieu alors que la personne infectée était contagieuse (c''est-à-dire lorsqu''elle avait des symptômes et/ou dans les 24 heures précédant l''apparition des symptômes).</p>',
        '<p>Un contatto stretto è dato se si vive nella stessa economia domestica o se si ha una relazione intima con questa persona. Il contatto deve essere avvenuto mentre la persona infetta era contagiosa (cioè quando ha avuto i sintomi e/o nelle 24 ore precedenti la comparsa dei sintomi).</p>',
        '<p>Close contact is given if you live in the same household or if you have an intimate relationship with this person. The contact must have taken place while the infected person was contagious (i.e. when he/she had symptoms and/or in the 24 hours before the symptoms occurred).</p>');

INSERT INTO recommendations (name, version, severity,
                     title_de, title_fr, title_it, title_en,
                     description_de, description_fr, description_it, description_en)
VALUES  ('RECOMMENDATION_SUSPECTED_KID', 'v3_kid', 'HIGH',
        '<div style=\"color: black\"><p><strong><span style=\"font-size: \n      18px;\">Resultat:</span></strong></p>\n\n<p>\nIhr Kind hat Symptome, die zu COVID-19 passen.\n</p>\n\n<p>\nEs ist möglich, dass es am neuen Coronavirus erkrankt sind\n</p>\n\n</div>',
        '',
        '',
        '',
        '<div style=\"color: black\"><p><strong><span style=\"font-size: \n      18px;\">Empfehlung</span>:</strong></p>\n<p>\nRufen Sie eine Kinderärztin bzw. einen Kinderarzt oder eine Gesundheitseinrichtung an, um das weitere Vorgehen zu besprechen. Sagen Sie, dass Ihr Kind Symptome hat. Befolgen Sie die Anweisungen der kontaktierten Fachperson. \n</p>\n\n\n</div>',
        '',
        '',
        ''),

       ('RECOMMENDATION_NOT_SUSPECTED_KID_CONTACT', 'v3_kid', 'HIGH',
        '<div style=\"color: black\"><p><strong><span style=\"font-size: \n      18px;\">Resultat:</span></strong></p>\n<p>Ihr Kind hatte in den vergangenen 10 Tagen engen Kontakt zu einer Person, die positiv auf das neue Coronavirus getestet wurde.\n<br>Auch wenn es momentan keine Symptome hat, ist es möglich, dass es sich mit dem neuen Coronavirus angesteckt hat.  \n</p></div>',
        '',
        '',
        '',
        '<div style=\"color: black\"><p><strong><span style=\"font-size: \n      18px;\">Empfehlung</span>:</strong></p>\n<p>\nSolange Ihr Kind keine Symptome hat, ist eine medizinische Abklärung nicht angezeigt. \n</p>\n\n<p>\nIhr Kind könnte ansteckend sein, ohne dass Sie es merken. Bitte schützen Sie Ihre Familie, Ihre Freunde und Ihr Umfeld, indem Ihr Kind in den 10 Tagen nach dem Kontakt mit der positiv getesteten Person unnötige Kontakte vermeidet. Die ersten Symptome treten am häufigsten innerhalb dieses Zeitrahmens auf. \n</p>\n\n<p>\nÜberwachen Sie den Gesundheitszustand Ihres Kindes.\n</p>\n\n<p>\nFalls es sich in Quarantäne begeben muss, werden Sie von der zuständigen kantonalen Stelle über die weiteren Schritte informiert.\n</p>\n\n<p>\nBefolgen Sie weiterhin die Hygiene- und Verhaltensregeln der Kampagne «<a rel=\"noopener noreferrer\" target=\"_blank\" href=\"http://www.bag.admin.ch/so-schuetzen-wir-uns\">So schützen wir uns</a>».\n</p>\n\n<p>\nMachen Sie diesen Check erneut, sobald das Kind Symptome einer akuten Atemwegserkrankung (z. B. Husten, Halsschmerzen, Kurzatmigkeit) und/oder Fieber, Fiebergefühl, Muskelschmerzen hat. Dasselbe gilt, wenn es plötzlich den Geruchs- und/oder Geschmackssinn verliert. \nSie erhalten dann eine der Situation angepasste Empfehlung.\n\n</p>\n</div>',
        '',
        '',
        ''),

       ('RECOMMENDATION_NOT_SUSPECTED_WITHOUT_FEVER', 'v3_kid', 'LOW',
        '<p><strong><span style=\"font-size: \n      18px;\">Resultat:</span></strong>\n  <br>Sie haben keines der typischen Symptome von COVID-19.\n  <br>Sie haben keine Kenntnis von einem engen Kontakt zu einer Person mit akuter Atemwegserkrankung.\n  <br> Es ist eher unwahrscheinlich, dass Sie am neuen Coronavirus erkrankt sind. \n</p>',
        '<div style=\"color: black\"><p><strong><span style=\"font-size: \n      18px;\">Résultat :</span></strong></p>\n  <p>Vous n’avez aucun des symptômes typiques du COVID-19.\n  <br>À votre connaissance, vous n’avez eu aucun contact avec une personne atteinte d’une maladie respiratoire aiguë.\n  <br>Il est peu probable que vous ayez contracté le nouveau coronavirus.\n</p></div>',
        '<p><strong><span style=\"font-size: \n      18px;\">Risultato:</span></strong>\n  <br>Dai dati immessi risulta che non ha nessuno dei sintomi tipici della COVID-19.\n  <br>Non Le risulta nemmeno di avere avuto un contatto stretto con una persona affetta da una malattia respiratoria acuta.\n  <br> È piuttosto improbabile che abbia contratto il nuovo coronavirus.\n</p>',
        '<div style=\"color: black\"><p><strong><span style=\"font-size: \n      18px;\">Result:</span></strong></p>\n  <p>You have none of the typical symptoms of COVID-19.\n  <br>You are not aware of having been in close contact with a person with acute respiratory disease. \n  <br>It is fairly unlikely that you have contracted the novel coronavirus.</p></div>',
        '<p><strong><span style=\"font-size: \n      18px;\">Empfehlung:</span>\n    <br>\n  </strong>Eine medizinische Abklärung oder ein Labortest ist nicht angezeigt.</p>\n<p>Halten Sie sich an die Hygiene- und Verhaltensregeln (siehe «<a href=\"https://www.bag.admin.ch/bag/de/home/krankheiten/ausbrueche-epidemien-pandemien/aktuelle-ausbrueche-epidemien/novel-cov/so-schuetzen-wir-uns.html\" rel=\"noopener noreferrer\" target=\"_blank\">So schützen wir uns</a>»).\n</p>',
        '<div style=\"color: black\"><p><strong><span style=\"font-size: \n      18px;\">Recommandation :</span></strong></p><p>Un examen médical ou une analyse en laboratoire n’est pas indiqué.</p>\n<p>Pensez à respecter les règles d’hygiène et de conduite (cf. \n  <a href=\"https://www.bag.admin.ch/bag/fr/home/krankheiten/ausbrueche-epidemien-pandemien/aktuelle-ausbrueche-epidemien/novel-cov/so-schuetzen-wir-uns.html\" rel=\"noopener noreferrer\" target=\"_blank\">Voici comment nous protéger</a>).&nbsp;</p></div>',
        '<p><strong><span style=\"font-size: \n      18px;\">Raccomandazione:</span>\n    <br>\n  </strong>Non sono indicati accertamenti medici o un test di laboratorio.</p>\n<p>Si attenga scrupolosamente alle regole d’igiene e di comportamento (cfr. campagna \n  <a href=\"https://www.bag.admin.ch/bag/it/home/krankheiten/ausbrueche-epidemien-pandemien/aktuelle-ausbrueche-epidemien/novel-cov/so-schuetzen-wir-uns.html\" rel=\"noopener noreferrer\" target=\"_blank\">Così ci proteggiamo</a><u>)</u>.</p>',
        '<div style=\"color: black\"><p><strong><span style=\"font-size: \n      18px;\">Recommendation:</span>\n  </strong></p>\n<p>Further medical investigation or a lab test is not indicated.</p>\n<p>Keep to the hygiene and behaviour rules (see \n  <a href=\"https://www.bag.admin.ch/bag/en/home/krankheiten/ausbrueche-epidemien-pandemien/aktuelle-ausbrueche-epidemien/novel-cov/so-schuetzen-wir-uns.html\" rel=\"noopener noreferrer\" target=\"_blank\">Protect yourself and others</a>).&nbsp;\n</p></div>');

