// model > enum
import {PermissionGroupType} from '../model/enum/permission-group-type.enum';
import {PermissionType} from '../model/enum/permission-type.enum';

export interface SystemPermission {

	group: PermissionGroupType,

	types: PermissionType[]
}