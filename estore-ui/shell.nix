with import <nixpkgs> {};
stdenv.mkDerivation rec {
  name = "ng";
  nativeBuildInputs = with pkgs; [
    nodejs_21
    yarn
    nodePackages."@angular/cli"
  ];
}