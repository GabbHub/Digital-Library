-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Giu 18, 2016 alle 15:51
-- Versione del server: 5.6.17
-- PHP Version: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `digital_library`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `gruppi`
--

CREATE TABLE IF NOT EXISTS `gruppi` (
  `ID` int(255) NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7 ;

--
-- Dump dei dati per la tabella `gruppi`
--

INSERT INTO `gruppi` (`ID`, `nome`) VALUES
(1, 'utenza avanzata'),
(2, 'acquisitore'),
(3, 'revisore-acquisizione'),
(4, 'trascrittore'),
(5, 'revisore-trascrizione'),
(6, 'amministratore');

-- --------------------------------------------------------

--
-- Struttura della tabella `immagini_digitali`
--

CREATE TABLE IF NOT EXISTS `immagini_digitali` (
  `ID` int(255) NOT NULL AUTO_INCREMENT,
  `file` text NOT NULL,
  `validazione` tinyint(1) NOT NULL,
  `numero_pagina` int(255) NOT NULL,
  `formato` varchar(255) NOT NULL,
  `dimensioni` bigint(20) NOT NULL,
  `opera` int(255) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `numero_pagina` (`numero_pagina`,`opera`),
  KEY `opera` (`opera`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dump dei dati per la tabella `immagini_digitali`
--

INSERT INTO `immagini_digitali` (`ID`, `file`, `validazione`, `numero_pagina`, `formato`, `dimensioni`, `opera`) VALUES
(1, 'Opere\\Divina_Commedia-Inferno\\DB.jpg', 1, 0, 'image/jpeg', 86645, 1),
(2, 'Opere\\Divina_Commedia-Inferno\\Digital_Library_Component-Deploy.jpg', 1, 2, 'image/jpeg', 306940, 1);

-- --------------------------------------------------------

--
-- Struttura della tabella `interventi`
--

CREATE TABLE IF NOT EXISTS `interventi` (
  `ID` int(255) NOT NULL AUTO_INCREMENT,
  `data` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `operazione` enum('REVISIONE IMMAGINI','REVISIONE TRASCRIZIONI','TRASCRIZIONE','ACQUISIZIONE') NOT NULL,
  `utente` int(255) NOT NULL,
  `opera` int(255) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `utente_2` (`utente`,`opera`),
  KEY `utente` (`utente`),
  KEY `opera` (`opera`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Struttura della tabella `notifiche_immagini`
--

CREATE TABLE IF NOT EXISTS `notifiche_immagini` (
  `ID` int(255) NOT NULL AUTO_INCREMENT,
  `mittente_destinatario` int(255) NOT NULL,
  `notifica` text NOT NULL,
  `immagine` int(255) DEFAULT NULL,
  `orario` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`),
  KEY `revisore` (`mittente_destinatario`),
  KEY `immagine` (`immagine`),
  KEY `immagine_2` (`immagine`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Struttura della tabella `notifiche_trascrizione`
--

CREATE TABLE IF NOT EXISTS `notifiche_trascrizione` (
  `ID` int(255) NOT NULL AUTO_INCREMENT,
  `mittente_destinatario` int(255) NOT NULL,
  `notifica` text COLLATE latin1_bin NOT NULL,
  `trascrizione` int(255) NOT NULL,
  `orario` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`),
  KEY `revisore` (`mittente_destinatario`),
  KEY `trascrizione` (`trascrizione`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_bin AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Struttura della tabella `opere`
--

CREATE TABLE IF NOT EXISTS `opere` (
  `ID` int(255) NOT NULL AUTO_INCREMENT,
  `Autore` varchar(255) NOT NULL,
  `Titolo` varchar(255) NOT NULL,
  `Anno` int(255) NOT NULL,
  `Lingua` varchar(25) NOT NULL,
  `numero_pagine` int(11) NOT NULL DEFAULT '1',
  `Pubblicazione_trascrizione` tinyint(1) NOT NULL,
  `Pubblicazione_immagini` tinyint(1) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `Titolo` (`Titolo`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dump dei dati per la tabella `opere`
--

INSERT INTO `opere` (`ID`, `Autore`, `Titolo`, `Anno`, `Lingua`, `numero_pagine`, `Pubblicazione_trascrizione`, `Pubblicazione_immagini`) VALUES
(1, 'Dante Alighieri', 'Divina Commedia-Inferno', 1200, 'Volgare Fiorentino', 2, 1, 1);

-- --------------------------------------------------------

--
-- Struttura della tabella `trascrizione`
--

CREATE TABLE IF NOT EXISTS `trascrizione` (
  `ID` int(255) NOT NULL AUTO_INCREMENT,
  `file` text NOT NULL,
  `validazione` tinyint(1) NOT NULL,
  `revisione` tinyint(1) NOT NULL DEFAULT '0',
  `immagine` int(255) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `immagine_2` (`immagine`),
  UNIQUE KEY `immagine_3` (`immagine`),
  KEY `immagine` (`immagine`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dump dei dati per la tabella `trascrizione`
--

INSERT INTO `trascrizione` (`ID`, `file`, `validazione`, `revisione`, `immagine`) VALUES
(1, '<ns1:TEI xmlns:ns1="http://www.tei-c.org/ns/1.0"\n    version="5.0">\n  <ns1:teiHeader>\n    <ns1:fileDesc>\n      <ns1:titleStmt>\n        <ns1:title>TITOLO due</ns1:title>\n      </ns1:titleStmt>\n      <ns1:publicationStmt>\n        <ns1:p />\n      </ns1:publicationStmt>\n      <ns1:sourceDesc>\n        <ns1:p />\n      </ns1:sourceDesc>\n    </ns1:fileDesc>\n    <ns1:encodingDesc>\n      <ns1:projectDesc>\n        <ns1:p />\n      </ns1:projectDesc>\n      <ns1:tagsDecl />\n      <ns1:appInfo>\n        <ns1:application ident="ident" version="5.0">\n          <ns1:desc />\n        </ns1:application>\n      </ns1:appInfo>\n    </ns1:encodingDesc>\n    <ns1:profileDesc />\n  </ns1:teiHeader>\n  <ns1:text>\n    <ns1:body>\n      <ns1:p>body</ns1:p>\n    </ns1:body>\n  </ns1:text>\n</ns1:TEI>', 1, 1, 1),
(2, '<ns1:TEI xmlns:ns1="http://www.tei-c.org/ns/1.0"\n    version="5.0">\n  <ns1:teiHeader>\n    <ns1:fileDesc>\n      <ns1:titleStmt>\n        <ns1:title />\n      </ns1:titleStmt>\n      <ns1:publicationStmt>\n        <ns1:p />\n      </ns1:publicationStmt>\n      <ns1:sourceDesc>\n        <ns1:p />\n      </ns1:sourceDesc>\n    </ns1:fileDesc>\n    <ns1:encodingDesc>\n      <ns1:projectDesc>\n        <ns1:p />\n      </ns1:projectDesc>\n      <ns1:tagsDecl />\n      <ns1:appInfo>\n        <ns1:application ident="ident" version="5.0">\n          <ns1:desc />\n        </ns1:application>\n      </ns1:appInfo>\n    </ns1:encodingDesc>\n    <ns1:profileDesc />\n  </ns1:teiHeader>\n  <ns1:text>\n    <ns1:body>\n      <ns1:p />\n    </ns1:body>\n  </ns1:text>\n</ns1:TEI>', 1, 1, 2);

-- --------------------------------------------------------

--
-- Struttura della tabella `utenti`
--

CREATE TABLE IF NOT EXISTS `utenti` (
  `ID` int(255) NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) NOT NULL,
  `cognome` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `indirizzo` varchar(255) DEFAULT NULL,
  `citta` varchar(255) DEFAULT NULL,
  `giornodinascita` date NOT NULL,
  `gruppo` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `gruppo` (`gruppo`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=15 ;

--
-- Dump dei dati per la tabella `utenti`
--

INSERT INTO `utenti` (`ID`, `nome`, `cognome`, `email`, `password`, `indirizzo`, `citta`, `giornodinascita`, `gruppo`) VALUES
(1, 'Valerio', 'Piccioni', 'vespa@vespa.com', '7a17d7f7df973ffc5270d950c14ebc3ecae428369e9503c5f16a4c565e290', '', '', '1992-01-13', 6),
(2, 'Gabriele', 'Giggino', 'giggino@gne.it', '1ef28f58cc3db9a48a72e9aa9ec634f2425e372987eda47be93e89bd8e8dfb', 'via giggino numero 941401941', 'Gigginolandia', '2016-05-11', 4),
(7, 'alfonso', 'pierantonio', 'alfonso@pierantonio.com', 'f094107ad255be530edc27ee74a8f4ad62d5949bc4f04239acb8f12788ea2f', 'via vetoio 1', 'L''Aquila', '1965-10-13', 1),
(8, 'ciro', 'immobile', 'ciro@napoli.it', 'b3cd56cef1eb7bb20cb3e8437095ecc573592a3cbd4f7a8d9917dfa4eff52', 'via napoli 1', 'napoli', '1997-01-09', 4),
(9, 'po', 'po', 'pippo@trascrittore.it', 'a2242ead55c94c3deb7cf234bfef9d5bcaca22dfe66e646745ee4371c633fc8', 'po', 'po', '1234-01-13', 1),
(10, 'val', 'pic', 't@t.it', 'd74ff0ee8da3b9806b18c877dbf29bbde5b5bd8e4dad7a3a7250feb82e8f1', 'via s ', 'alb', '2016-06-23', 1),
(11, 'utente1', 'utente1', 'utente1@utente1.it', '9cdee55fb57181e54646f487753dde73bc8e8c73843de92d1427420c64c23', '', '', '2016-06-16', 1),
(12, 'utente2', 'utente2', 'utente2@utente2.it', '6cf615d5bcaac778352a8f1f3360d23f2f34ec182e259897fd6ce485d7870d4', '', '', '2016-06-15', 5),
(13, 'utente3', 'utente3', 'utente3@utente3.it', '596ac361a137e2d286465cd6588ebb5ac3f5ae9550110bc41577c3d751764', '', '', '2016-06-16', 1),
(14, 'utente4', 'utente4', 'utente4@utente4.it', 'b97873a4f73abedd8d685a7cd5e5f85e4a9cfb83eac26886640a0813850122b', '', '', '2016-06-07', 5);

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `immagini_digitali`
--
ALTER TABLE `immagini_digitali`
  ADD CONSTRAINT `appartenenza` FOREIGN KEY (`opera`) REFERENCES `opere` (`ID`);

--
-- Limiti per la tabella `interventi`
--
ALTER TABLE `interventi`
  ADD CONSTRAINT `interventi_ibfk_1` FOREIGN KEY (`utente`) REFERENCES `utenti` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `interventi_ibfk_2` FOREIGN KEY (`opera`) REFERENCES `opere` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `notifiche_immagini`
--
ALTER TABLE `notifiche_immagini`
  ADD CONSTRAINT `immagini_revisioni` FOREIGN KEY (`immagine`) REFERENCES `immagini_digitali` (`ID`),
  ADD CONSTRAINT `revisioni_utenti` FOREIGN KEY (`mittente_destinatario`) REFERENCES `utenti` (`ID`);

--
-- Limiti per la tabella `notifiche_trascrizione`
--
ALTER TABLE `notifiche_trascrizione`
  ADD CONSTRAINT `revisione_trascrizioni` FOREIGN KEY (`trascrizione`) REFERENCES `trascrizione` (`ID`),
  ADD CONSTRAINT `revisore_utente` FOREIGN KEY (`mittente_destinatario`) REFERENCES `utenti` (`ID`);

--
-- Limiti per la tabella `trascrizione`
--
ALTER TABLE `trascrizione`
  ADD CONSTRAINT `associazione` FOREIGN KEY (`immagine`) REFERENCES `immagini_digitali` (`ID`);

--
-- Limiti per la tabella `utenti`
--
ALTER TABLE `utenti`
  ADD CONSTRAINT `utenti_gruppi` FOREIGN KEY (`gruppo`) REFERENCES `gruppi` (`ID`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
