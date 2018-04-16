export interface IProfile {
  id: number;
  username: string;
  displayName: string;
  emailAddress: string;
  location: string;
  contactLink: string;
  bio: string;
  password: string;
}

export class Profile {
  constructor(public serverData: IProfile) {
  }
}
