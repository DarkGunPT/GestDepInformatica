SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';





CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`Utilizadores`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Utilizadores` (
  `Id_Utilizador` INT NOT NULL AUTO_INCREMENT,
  `Username` VARCHAR(30) NOT NULL,
  `Password` VARCHAR(30) NOT NULL,
  `NomeCompleto` VARCHAR(45) NULL,
  `DataNascimento` DATE NULL,
  `Genero` VARCHAR(45) NULL,
  `Contrato` VARCHAR(45) NULL,
  `Servico` VARCHAR(100) NULL,
  `Morada` VARCHAR(250) NULL,
  `Email` VARCHAR(50) NULL,
  `Telefone` VARCHAR(9) NULL,
  `Salario` DOUBLE NULL,
  `isAdmin` TINYINT NOT NULL,
  PRIMARY KEY (`Id_Utilizador`),
  UNIQUE INDEX `Username_UNIQUE` (`Username` ASC) VISIBLE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
