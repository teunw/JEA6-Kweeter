import {IProfile} from './profile';

export interface IAuthToken {
  token: string;
  profile: IProfile;
  issueDate: Date;
  experationDate: Date;
}
