BEGIN;

INSERT INTO movie_entity (id, title, description, length, director, premiere_year, categories, img_main) VALUES
    (
        1,
        'Dziekan II: Electric Boogalo',
        'W filmie powracamy do sennego miasteczka, gdzie Dziekan - zmutowany szkielet o nieokiełznanym poczuciu humoru - powraca, by siać chaos i przerażenie. Tym razem jednak, gdy zaczynają pojawiać się tajemnicze zjawiska elektryczne, Dziekan musi stawić czoła nowemu',
        1832, 'Arkadiusz Wójs', 2023, ARRAY['dramat', 'film grozy', 'komedia'],
        '/movies/dziekan-2.webp'
    ),
    (
        2,
        'Matrix: Politechnika Wrocławska',
        'W świecie, gdzie rzeczywistość jest nieustannie kwestionowana, studenci Politechniki Wrocławskiej odkrywają, że ich uczelnia kryje tajemniczą prawdę. Podczas gdy inne uczelnie żyją w zwykłym świecie, studenci PW zostają wprowadzeni w wir inżynieryjnych wyzwań, ',
        1938, 'Jerzy Pietraszko', 2011, ARRAY['matematyka', 'komedia', 'thriller'],
        '/movies/matrix-pwr.webp'
    ),
    (
        3,
        'Harry Potter i Magia Inżynierii',
        'W magicznym świecie Politechniki Wrocławskiej, młodzi czarodzieje uczą się sztuki łączenia magii z inżynierią. Gdy tajemnicza siła zaczyna zakłócać harmonię między światem magicznym a technologicznym, Harry, Hermiona i Ron muszą zjednoczyć siły, aby ocalić szkołę ',
        2048, 'Jerzy Pietraszko', 2011, ARRAY['matematyka', 'komedia', 'thriller'],
        '/movies/hp-magia-inzynierii.webp'
    ),
    (
        4,
        'Jurassic Lab: DNA Of Wrocław',
        'Na terenie Politechniki Wrocławskiej prowadzone są rewolucyjne badania nad odtworzeniem dinozaurów za pomocą DNA. Gdy eksperyment wymyka się spod kontroli, studenci muszą stawić czoła wypuszczonym na świat pradawnym bestiom, korzystając z najnowszych osiągnięć ',
        2137, 'Jerzy Pietraszko', 2011, ARRAY['matematyka', 'komedia', 'thriller'],
        '/movies/jurrasic-lab.webp'
    ), 
    (
        5,
        'Avengers: Inżynierowie Wszechświata',
        'Kiedy kosmiczne zagrożenie zagraża równowadze wszechświata, grupa niezwykłych studentów Politechniki Wrocławskiej zostaje powołana do życia. Wyposażeni w najnowsze wynalazki i umiejętności inżynieryjne, stają się niezwykłymi bohaterami gotowymi bronić Ziemi ',
        1420, 'Jerzy Pietraszko', 2011, ARRAY['matematyka', 'komedia', 'thriller'],
        '/movies/avengers-pwr.webp'
    );

INSERT INTO airing_entity (id, movie_id, type, time) VALUES 
    -- movie 1:
    (1, 1, 0, CURRENT_TIMESTAMP + INTERVAL '1 day' + interval '0 hour'),
    (2, 1, 1, CURRENT_TIMESTAMP + INTERVAL '1 day' + interval '1 hour'),
    (3, 1, 2, CURRENT_TIMESTAMP + INTERVAL '1 day' + interval '2 hour'),
    -- movie 2:
    (4, 2, 0, CURRENT_TIMESTAMP + INTERVAL '1 day' + interval '0 hour'),
    (5, 2, 1, CURRENT_TIMESTAMP + INTERVAL '1 day' + interval '1 hour'),
    (6, 2, 2, CURRENT_TIMESTAMP + INTERVAL '1 day' + interval '2 hour'),
    -- movie 3:
    (7, 3, 0, CURRENT_TIMESTAMP + INTERVAL '1 day' + interval '0 hour'),
    (8, 3, 1, CURRENT_TIMESTAMP + INTERVAL '1 day' + interval '1 hour'),
    (9, 3, 2, CURRENT_TIMESTAMP + INTERVAL '1 day' + interval '2 hour'),
    -- movie 4
    (10, 4, 0, CURRENT_TIMESTAMP + INTERVAL '1 day' + interval '0 hour'),
    (11, 4, 1, CURRENT_TIMESTAMP + INTERVAL '1 day' + interval '1 hour'),
    (12, 4, 2, CURRENT_TIMESTAMP + INTERVAL '1 day' + interval '2 hour'),
    -- movie 5
    (13, 5, 0, CURRENT_TIMESTAMP + INTERVAL '1 day' + interval '0 hour'),
    (14, 5, 1, CURRENT_TIMESTAMP + INTERVAL '1 day' + interval '1 hour'),
    (15, 5, 2, CURRENT_TIMESTAMP + INTERVAL '1 day' + interval '2 hour'),
    (16, 5, 2, CURRENT_TIMESTAMP);

INSERT INTO movie_entity_airings (airings_id, movie_entity_id) VALUES 
    -- movie 1
    (1, 1), (2, 1), (3, 1),
    -- movie 2
    (4, 2), (5, 2), (6, 2),
    -- movie 3
    (7, 3), (8, 3), (9, 3),
    -- movie 4
    (10, 4), (11, 4), (12, 4),
    -- movie 5
    (13, 5), (14, 5), (15, 5);

INSERT INTO ticket_entity(status, id, code, airing_id, first_name, last_name, phone_number, seats_taken) VALUES 
    (2,  1,	'BK93UK82HX',	1, 'Albert',	'Gębura',	'184398686',    ARRAY[ 0,  1,  2,  3,  4,  5,  6]),
    (2,  2,	'EYRS09HXZC',	1, 'Miłosz',	'Kajdas',	'180437827',    ARRAY[ 7,  8,  9, 10, 11, 12, 13]),
    (2,  3,	'5UHO4H8AVP',	1, 'Paweł',	    'Wicha',	'558911827',    ARRAY[14, 15, 16, 17, 18, 19, 20]),
    (2,  4,	'7ZOOJHS1JK',	1, 'Monika',	'Peda',	    '973301224',    ARRAY[21, 22, 23, 24, 25, 26, 27]),
    (2,  5,	'M0ASZI5ES0',	1, 'Krystian',	'Smagacz',	'101721134',    ARRAY[28, 29]),

    (2,  6,	'21PZXWFCC7',	2, 'Leonard',	'Żółtek',	'194250443',    ARRAY[ 0,  1,  2,  3,  4,  5,  6]),
    (2,  7,	'QYRIUZDZTL',	2, 'Ksawery',	'Ziegler',	'250149007',    ARRAY[ 7,  8,  9, 10, 11, 12, 13]),

    (2,  8,	'4FLH3ZWI0J',	3, 'Adrian',	'Lamch',	'671755685',    ARRAY[ 0,  1,  2,  3,  4,  5,  6]),

    (2,  9,	'U1O6XIAY6U',	4, 'Malwina',	'Tytko',	'056859403',    ARRAY[ 0,  1,  2,  3,  4,  5,  6]),
    (2, 10,	'ZMH04VA144',	4, 'Lidia',	    'Lejman',	'011161718',    ARRAY[ 7,  8,  9, 10, 11, 12, 13]),
    (2, 11,	'DFV1GKH6P1',	4, 'Olgierd',	'Picheta',	'913327629',    ARRAY[14, 15, 16, 17, 18, 19, 20]),
    (2, 12,	'B0HXV0ER8E',	4, 'Mariusz',	'Milka',	'283558135',    ARRAY[21, 22, 23, 24, 25, 26, 27]),
    (2, 13,	'XUGJKKA3BR',	4, 'Dorota',	'Golonko',	'441841118',    ARRAY[28, 29]),

    (2, 14,	'0M27ZVVYAN',	5, 'Natasza',   'Marcinisz','447583175',    ARRAY[ 0,  1,  2,  3,  4,  5,  6]),
    (2, 15,	'3JLOGCTQXT',	5, 'Ewa',       'Guściora', '973505066',    ARRAY[ 7,  8,  9, 10, 11, 12, 13]),

    (2, 16,	'KMTGMK38GN',	6, 'Piotr',	    'Nicewicz',	'164551778',    ARRAY[ 0,  1,  2,  3,  4,  5,  6]),

    (2, 17,	'F6OI5J9YI3',	7, 'Aurelia',	'Stiller',	'154806694',    ARRAY[ 0,  1,  2,  3,  4,  5,  6]),
    (2, 18,	'G46CZR0LJG',	7, 'Stanisław', 'Janka',	'437410027',    ARRAY[ 7,  8,  9, 10, 11, 12, 13]),
    (2, 19,	'JCJSYK79NE',	7, 'Elżbieta',	'Dzwonnik',	'260769288',    ARRAY[14, 15, 16, 17, 18, 19, 20]),
    (2, 20,	'SY28S24JB3',	7, 'Justyna',	'Stefanik',	'203268538',    ARRAY[21, 22, 23, 24, 25, 26, 27]),
    (2, 21,	'9U86Q00PBS',	7, 'Adrianna',	'Jaśkowiec','188053633',    ARRAY[28, 29]),

    (2, 22,	'V8KPLJXLTV',	8, 'Stefan',	'Garncarz',	'425218507',    ARRAY[ 0,  1,  2,  3,  4,  5,  6]),
    (2, 23,	'QN9IS016N1',	8, 'Tymoteusz',   'Damps',  '455799381',    ARRAY[ 7,  8,  9, 10, 11, 12, 13]),

    (2, 24,	'9O6BPVX94E',	9, 'Norbert',	'Wochnik',	'092528044',    ARRAY[ 0,  1,  2,  3,  4,  5,  6]),

    (2, 25,	'C1UX8WXZN7',   10, 'Mikołaj',	'Haremza',	'202956804',    ARRAY[ 0,  1,  2,  3,  4,  5,  6]),
    (2, 26,	'GAMJAB8VCW',   10, 'Monika',	'Durma',	'521167945',    ARRAY[ 7,  8,  9, 10, 11, 12, 13]),
    (2, 27,	'Q1Z27A9CM8',   10, 'Kornelia',	'Ślipek',	'094257463',    ARRAY[14, 15, 16, 17, 18, 19, 20]),
    (2, 28,	'NJGM0GLLV3',   10, 'Klara',	'Kunda',	'988477551',    ARRAY[21, 22, 23, 24, 25, 26, 27]),
    (2, 29,	'SOLSYDHLOO',   10, 'Nataniel',	'Kruszka',	'698867576',    ARRAY[28, 29]),

    (2, 30,	'LLFOM3ZUKK',	11, 'Szymon',	'Dyjas',	'812073695',    ARRAY[ 0,  1,  2,  3,  4,  5,  6]),
    (2, 31,	'TAL93Y5PEQ',	11, 'Alex',	    'Szymonik',	'450879914',    ARRAY[ 7,  8,  9, 10, 11, 12, 13]),

    (2, 32,	'0I6ADY0M4L',	12, 'Julian',	'Krasa',	'336031516',    ARRAY[ 0,  1,  2,  3,  4,  5,  6]),

    (2, 33,	'UELDMH8A34',	13, 'Dominik',	'Orzeszek',	'772224802',    ARRAY[ 0,  1,  2,  3,  4,  5,  6]),
    (2, 34,	'6OLIIYIA11',	13, 'Fabian',	'Niewiara',	'614666211',    ARRAY[ 7,  8,  9, 10, 11, 12, 13]),
    (2, 35,	'D1UA90LE5J',	13, 'Kamil',	'Białach',	'574028593',    ARRAY[14, 15, 16, 17, 18, 19, 20]),
    (2, 36,	'QPBE8AHIPP',	13, 'Julita',	'Kotlarek',	'802262521',    ARRAY[21, 22, 23, 24, 25, 26, 27]),
    (2, 37,	'TDO1MM1VHU',	13, 'Maciej',	'Wojtczuk',	'898489014',    ARRAY[28]),

    (2, 38,	'N1XNV7MHSK',	14, 'Norbert',	'Soczówka',	'686841079',    ARRAY[ 0,  1,  2,  3,  4,  5,  6]),
    (2, 39,	'UJ9SGDTMRC',	14, 'Kamila',	'Jonik',	'150137735',    ARRAY[ 7,  8,  9, 10, 11, 12, 13]),

    (2, 40,	'SESRV4H9EO',	15, 'Natan',	'Jagiełka',	'025306425',    ARRAY[ 0,  1,  2,  3,  4,  5,  6]),
    (2, 41,	'TEST213769',	16, 'Karol',	'Wojtyła',	'213721370',    ARRAY[ 0,  1,  2,  3,  4,  5,  6]),
    (3, 42,	'TEST692137',	16, 'Jan',	'Paweł-Drugi',	'213721371',    ARRAY[ 7 ]);


INSERT INTO airing_entity_tickets(airing_entity_id, tickets_id) VALUES 
    (1, 1), (1, 2), (1, 3), (1, 4), (1, 5),
    (2, 6), (2, 7),
    (3, 8),

    (4, 9), (4, 10), (4, 11), (4, 12), (4, 13),
    (5, 14), (5, 15),
    (6, 16),

    (7, 17), (7, 18), (7, 19), (7, 20), (7, 21),
    (8, 22), (8, 23),
    (9, 24),

    (10, 25), (10, 26), (10, 27), (10, 28), (10, 29),
    (11, 30), (11, 31),
    (12, 32),

    (13, 33), (13, 34), (13, 35), (13, 36), (13, 37),
    (14, 38), (14, 39),
    (15, 40);

INSERT INTO user_entity (id, password, email) VALUES
    (100, '$2a$10$MplFksZFUiiceFzV.Tuc7uYDBLcsdEi8OsV/JoXZuQUmA8w4zrpSG', 'admin123@example.com');


-- ALTER SEQUENCE ticket_entity_seq RESTART WITH 1000;
-- ALTER SEQUENCE movie_entity_seq RESTART WITH 1000;
-- ALTER SEQUENCE airing_entity_seq RESTART WITH 1000;
-- ALTER SEQUENCE user_entity_seq RESTART WITH 1000;

COMMIT;